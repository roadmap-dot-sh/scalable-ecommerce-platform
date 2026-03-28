package com.example.paymentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.stripe")
@Data
public class StripeConfig {
    private String apiKey = "";
    private String webhookSecret = "";
}
