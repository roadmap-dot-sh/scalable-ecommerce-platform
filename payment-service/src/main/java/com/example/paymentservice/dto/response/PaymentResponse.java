package com.example.paymentservice.dto.response;

import com.example.paymentservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String provider;
    private String externalTransactionId;
    private LocalDateTime createdAt;
}
