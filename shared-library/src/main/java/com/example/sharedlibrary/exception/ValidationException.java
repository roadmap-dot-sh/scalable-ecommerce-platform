/*
 * ValidationException.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.exception;

import lombok.Getter;

import java.util.Map;

/**
 * ValidationException.java
 *
 * @author Nguyen
 */
@Getter
public class ValidationException extends BusinessException {
    private final Map<String, String> validationErrors;

    public ValidationException(String message, Map<String, String> validationErrors) {
        super("VALIDATION_ERROR", message);
        this.validationErrors = validationErrors;
    }
}
