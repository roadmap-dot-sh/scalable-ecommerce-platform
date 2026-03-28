package com.example.notificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TemplateRequest {

    @NotBlank
    private String code;

    private String name;

    private String subjectTemplate;

    @NotBlank
    private String bodyTemplate;
}
