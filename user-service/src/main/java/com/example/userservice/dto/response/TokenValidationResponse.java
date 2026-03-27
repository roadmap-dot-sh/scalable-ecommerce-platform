/*
 * TokenValidationResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * TokenValidationResponse.java
 *
 * @author Nguyen
 */
@Data
@Builder
public class TokenValidationResponse {
    private boolean valid;
    private String username;
    private String userId;
}
