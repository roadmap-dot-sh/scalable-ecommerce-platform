package com.example.paymentservice.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaypalWebhookHandler {

    public void onEvent(String payload) {
        log.debug("PayPal webhook event received (length={})", payload != null ? payload.length() : 0);
    }
}
