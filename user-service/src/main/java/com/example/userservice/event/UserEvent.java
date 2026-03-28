/*
 * UserEvent.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.userservice.event;

import com.example.sharedlibrary.enums.UserEventType;
import com.example.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * UserEvent.java
 *
 * @author Nguyen
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserEvent {
    private final KafkaTemplate<String, com.example.sharedlibrary.dto.event.UserEvent> kafkaTemplate;

    public void sendUserEvent(User user, UserEventType eventType) {
        com.example.sharedlibrary.dto.event.UserEvent event = com.example.sharedlibrary.dto.event.UserEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType.name())
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .userEventType(eventType)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send("user-events", event);
        log.info("User event send: {}", event);
    }
}
