package com.example.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {
    private String id;
    private String userId;
    private String brand;
    private String last4;
    private String provider;
    private boolean defaultMethod;
    private LocalDateTime createdAt;
}
