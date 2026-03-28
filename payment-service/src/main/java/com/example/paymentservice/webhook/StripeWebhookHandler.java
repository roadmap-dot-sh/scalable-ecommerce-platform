package com.example.paymentservice.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StripeWebhookHandler {

    public void onEvent(String payload) {
        log.debug("Stripe webhook event received (length={})", payload != null ? payload.length() : 0);
    }
}
