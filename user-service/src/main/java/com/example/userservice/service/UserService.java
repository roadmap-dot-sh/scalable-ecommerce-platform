/*
 * UserService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.service;

import com.example.sharedlibrary.enums.UserEventType;
import com.example.userservice.dto.request.*;
import com.example.userservice.dto.response.AuthResponse;
import com.example.userservice.dto.response.TokenValidationResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.EmailVerificationToken;
import com.example.userservice.entity.PasswordResetToken;
import com.example.userservice.entity.User;
import com.example.userservice.event.UserEvent;
import com.example.userservice.exception.*;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.EmailVerificationTokenRepository;
import com.example.userservice.repository.PasswordResetTokenRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * UserService.java
 *
 * @author Nguyen
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserEvent eventPublisher;
    private final UserMapper userMapper;


    @Transactional
    public UserResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .build();

        user = userRepository.save(user);

        // Generate email verification token
        String verificationToken = UUID.randomUUID().toString();
        EmailVerificationToken emailToken = new EmailVerificationToken(verificationToken, user);

        // Save token to database (implement repository)
        // publish user created event
        eventPublisher.sendUserEvent(user, UserEventType.REGISTERED);

        log.info("User registered successfully with id: {}", user.getId());

        return userMapper.mapToResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getEmailOrUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmailOrUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmailOrUsername(
                    request.getEmailOrUsername(),
                    request.getEmailOrUsername()
            ).orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmailOrUsername()));

            // Reset failed login attempts
            user.setFailedLoginAttempts(0);
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            eventPublisher.sendUserEvent(user, UserEventType.LOGIN);

            return AuthResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .user(userMapper.mapToResponse(user))
                    .build();
        } catch (Exception e) {
            log.error("Login failed for user: {}", request.getEmailOrUsername(), e);
            throw new InvalidCredentialsException("Invalid email/username or password");
        }
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse getUser(String id) {
        log.info("Fetching user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userMapper.mapToResponse(user);
    }

    @Cacheable(value = "users", key = "#email")
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return userMapper.mapToResponse(user);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        user = userRepository.save(user);

        // publish user updated event
        eventPublisher.sendUserEvent(user, UserEventType.UPDATED);

        return userMapper.mapToResponse(user);
    }


    @Transactional
    public void changePassword(String userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // publish password change event
        eventPublisher.sendUserEvent(user, UserEventType.PASSWORD_CHANGED);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        // Delete existing tokens
        passwordResetTokenRepository.deleteByUser_Id(user.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(resetToken);

        // Send email with reset token (would be sent via notification service)
        log.info("Password reset token for user {}: {}", request.getEmail(), token);

        // send password reset email
        eventPublisher.sendUserEvent(user, UserEventType.PASSWORD_FORGOT);
    }

    @Transactional
    public void resetPassword(String token, ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid token"));

        if (resetToken.isExpired()) {
            throw new InvalidCredentialsException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Delete used token
        passwordResetTokenRepository.delete(resetToken);

        // publish password reset event
        eventPublisher.sendUserEvent(user, UserEventType.PASSWORD_RESET);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public TokenValidationResponse validateTokenWithDetails(String token) {
        boolean isValid = jwtService.validateToken(token);

        if (isValid) {
            String username = jwtService.extractUsername(token);
            String userId = jwtService.extractUserId(token);

            return TokenValidationResponse.builder()
                    .valid(true)
                    .username(username)
                    .userId(userId)
                    .build();
        }

        return TokenValidationResponse.builder()
                .valid(false)
                .build();
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::mapToResponse);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setAccountEnabled(false);
        userRepository.save(user);

        // publish user deleted event
        eventPublisher.sendUserEvent(user, UserEventType.DELETED);
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {

        }
    }
}
