package com.example.notificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PushNotificationRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;
}
