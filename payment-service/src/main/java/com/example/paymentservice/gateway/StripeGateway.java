package com.example.paymentservice.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
public class StripeGateway implements PaymentGateway {

    @Override
    public String getName() {
        return "STRIPE";
    }

    @Override
    public GatewayChargeResult charge(BigDecimal amount, String currency, String orderId) {
        log.info("Stripe mock charge orderId={} amount={} {}", orderId, amount, currency);
        return new GatewayChargeResult(true, "ch_mock_" + UUID.randomUUID(), "ok");
    }
}
