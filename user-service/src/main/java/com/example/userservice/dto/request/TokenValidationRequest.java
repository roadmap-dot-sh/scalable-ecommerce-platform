/*
 * TokenValidationRequest.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.dto.request;

import lombok.Data;

/**
 * TokenValidationRequest.java
 *
 * @author Nguyen
 */
@Data
public class TokenValidationRequest {
    private String token;
}
