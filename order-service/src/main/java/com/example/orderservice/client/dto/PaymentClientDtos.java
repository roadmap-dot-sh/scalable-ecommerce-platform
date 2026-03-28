package com.example.orderservice.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentClientDtos {

    @Data
    public static class ChargeRequest {
        private String orderId;
        private BigDecimal amount;
        private String currency;
        private String provider;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChargeResponse {
        private String id;
        private String orderId;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String provider;
        private String externalTransactionId;
        private LocalDateTime createdAt;
    }
}
