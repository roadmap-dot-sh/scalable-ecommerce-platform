/*
 * ResourceNotFoundException.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.exception;

/**
 * ResourceNotFoundException.java
 *
 * @author Nguyen
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String user, String email, String emailOrUsername) {
        
    }
}
