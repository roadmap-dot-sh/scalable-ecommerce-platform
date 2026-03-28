/*
 * UserEvent.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.event;

import com.example.sharedlibrary.enums.UserEventType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * UserEvent.java
 *
 * @author Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEvent {
    String eventId;
    String eventType;
    String userId;
    String email;
    String username;
    UserEventType userEventType;
    LocalDateTime timestamp;
}
