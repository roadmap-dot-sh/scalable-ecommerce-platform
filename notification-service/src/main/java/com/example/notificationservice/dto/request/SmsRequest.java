package com.example.notificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SmsRequest {

    @NotBlank
    private String to;

    @NotBlank
    private String body;
}
