package com.example.notificationservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String id;
    private String channel;
    private String recipient;
    private String subject;
    private String body;
    private String status;
    private LocalDateTime createdAt;
}
