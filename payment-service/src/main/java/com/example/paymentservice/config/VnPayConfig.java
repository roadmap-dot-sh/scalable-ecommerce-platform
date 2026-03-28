package com.example.paymentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.vnpay")
@Data
public class VnPayConfig {
    private String tmnCode = "";
    private String secretKey = "";
    private String paymentUrl = "";
}
