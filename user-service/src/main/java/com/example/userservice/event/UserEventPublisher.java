/*
 * UserEventPublisher.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.event;

import com.example.userservice.entity.User;
import org.springframework.stereotype.Component;

/**
 * UserEventPublisher.java
 *
 * @author Nguyen
 */
@Component
public class UserEventPublisher {
    public void publishUserCreatedEvent(User user) {

    }

    public void publishUserUpdatedEvent(User user) {

    }

    public void publishPasswordChangedEvent(User user) {

    }

    public void publishPasswordResetEvent(User user, String token) {

    }

    public void publishPasswordResetConfirmedToken(User user) {

    }

    public void publishUserDeletedEvent(User user) {

    }
}
