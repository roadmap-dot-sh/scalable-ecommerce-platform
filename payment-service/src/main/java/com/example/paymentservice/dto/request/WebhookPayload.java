package com.example.paymentservice.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class WebhookPayload {
    private String provider;
    private Map<String, Object> raw;
}
