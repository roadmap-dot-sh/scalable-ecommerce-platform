package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.WebhookPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookService {

    public void handleStripe(String rawBody) {
        log.info("[webhook:stripe] {}", rawBody != null ? rawBody.substring(0, Math.min(500, rawBody.length())) : "null");
    }

    public void handlePaypal(String rawBody) {
        log.info("[webhook:paypal] {}", rawBody != null ? rawBody.substring(0, Math.min(500, rawBody.length())) : "null");
    }

    public void handleVnPay(WebhookPayload payload) {
        log.info("[webhook:vnpay] {}", payload);
    }
}
