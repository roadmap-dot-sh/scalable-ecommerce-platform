package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse charge(@Valid @RequestBody PaymentRequest request) {
        return paymentService.charge(request);
    }

    @GetMapping("/{id}")
    public PaymentResponse get(@PathVariable String id) {
        return paymentService.getById(id);
    }

    @GetMapping("/orders/{orderId}/latest")
    public PaymentResponse latestForOrder(@PathVariable String orderId) {
        return paymentService.getLatestForOrder(orderId);
    }
}
