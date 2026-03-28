/*
 * EmailVerificationTokenRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.repository;

import com.example.userservice.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * EmailVerificationTokenRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, String> {
    Optional<EmailVerificationToken> findByToken(String token);

    void deleteByUser_Id(String userId);
}
