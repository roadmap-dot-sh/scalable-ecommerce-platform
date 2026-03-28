package com.example.paymentservice.gateway.factory;

import com.example.paymentservice.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentGatewayFactory {

    private final Map<String, PaymentGateway> byName;

    public PaymentGatewayFactory(List<PaymentGateway> gateways) {
        this.byName = gateways.stream()
                .collect(Collectors.toMap(g -> g.getName().toUpperCase(Locale.ROOT), Function.identity()));
    }

    public PaymentGateway get(String provider) {
        if (provider == null || provider.isBlank()) {
            return byName.get("STRIPE");
        }
        PaymentGateway g = byName.get(provider.toUpperCase(Locale.ROOT));
        if (g == null) {
            throw new IllegalArgumentException("Unknown payment provider: " + provider);
        }
        return g;
    }
}
