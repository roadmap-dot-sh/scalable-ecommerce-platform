package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.WebhookPayload;
import com.example.paymentservice.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/stripe")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stripe(@RequestBody String body) {
        webhookService.handleStripe(body);
    }

    @PostMapping("/paypal")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void paypal(@RequestBody String body) {
        webhookService.handlePaypal(body);
    }

    @PostMapping("/vnpay")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void vnpay(@RequestBody WebhookPayload payload) {
        webhookService.handleVnPay(payload);
    }
}
