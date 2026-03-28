package com.example.paymentservice.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
public class VnPayGateway implements PaymentGateway {

    @Override
    public String getName() {
        return "VNPAY";
    }

    @Override
    public GatewayChargeResult charge(BigDecimal amount, String currency, String orderId) {
        log.info("VNPAY mock charge orderId={} amount={} {}", orderId, amount, currency);
        return new GatewayChargeResult(true, "vnp_mock_" + UUID.randomUUID(), "ok");
    }
}
