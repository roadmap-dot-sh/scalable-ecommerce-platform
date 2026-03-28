package com.example.orderservice.client;

import com.example.orderservice.client.dto.PaymentClientDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PaymentServiceClient {

    private final WebClient webClient;

    @Value("${services.payment.url:http://localhost:8085}")
    private String paymentBaseUrl;

    public PaymentClientDtos.ChargeResponse charge(
            String orderId,
            BigDecimal amount,
            String currency,
            String provider) {

        PaymentClientDtos.ChargeRequest body = new PaymentClientDtos.ChargeRequest();
        body.setOrderId(orderId);
        body.setAmount(amount);
        body.setCurrency(currency != null ? currency : "USD");
        body.setProvider(provider != null ? provider : "STRIPE");

        try {
            return webClient.post()
                    .uri(paymentBaseUrl + "/api/payments/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(PaymentClientDtos.ChargeResponse.class)
                    .block(Duration.ofSeconds(30));
        } catch (WebClientResponseException e) {
            throw new IllegalStateException("Payment failed: " + e.getResponseBodyAsString(), e);
        }
    }
}
