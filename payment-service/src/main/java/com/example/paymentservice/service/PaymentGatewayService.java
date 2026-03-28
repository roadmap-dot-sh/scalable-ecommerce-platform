package com.example.paymentservice.service;

import com.example.paymentservice.gateway.PaymentGateway;
import com.example.paymentservice.gateway.factory.PaymentGatewayFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentGatewayService {

    private final PaymentGatewayFactory factory;

    public PaymentGateway.GatewayChargeResult charge(
            String provider,
            BigDecimal amount,
            String currency,
            String orderId) {
        PaymentGateway gw = factory.get(provider);
        return gw.charge(amount, currency != null ? currency : "USD", orderId);
    }
}
