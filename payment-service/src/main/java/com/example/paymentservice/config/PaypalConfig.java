package com.example.paymentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.paypal")
@Data
public class PaypalConfig {
    private String clientId = "";
    private String clientSecret = "";
    private String mode = "sandbox";
}
