/*
 * SecurityConfig.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * SecurityConfig.java
 *
 * @author Nguyen
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/actuator/**", "/fallback/**").permitAll()
                        .pathMatchers("/api/users/register", "/api/users/login").permitAll()
                        .anyExchange().authenticated()
                );

        return http.build();
    }
}
