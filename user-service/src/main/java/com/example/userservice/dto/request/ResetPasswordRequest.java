/*
 * ResetPasswordRequest.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.dto.request;

import lombok.Data;

/**
 * ResetPasswordRequest.java
 *
 * @author Nguyen
 */
@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
