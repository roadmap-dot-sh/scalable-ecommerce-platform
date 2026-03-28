/*
 * ErrorResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * ErrorResponse.java
 *
 * @author Nguyen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    String code;
    String message;
    String detail;
    Map<String, String> validationErrors;
}
