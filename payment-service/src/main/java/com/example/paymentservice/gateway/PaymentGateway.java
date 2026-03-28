package com.example.paymentservice.gateway;

import java.math.BigDecimal;

public interface PaymentGateway {

    String getName();

    GatewayChargeResult charge(BigDecimal amount, String currency, String orderId);

    record GatewayChargeResult(boolean success, String externalId, String message) {
    }
}
