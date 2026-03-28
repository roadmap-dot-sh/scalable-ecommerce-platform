package com.example.paymentservice.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VnPayWebhookHandler {

    public void onEvent(Object payload) {
        log.debug("VNPAY webhook: {}", payload);
    }
}
