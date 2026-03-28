package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.PaymentMethodRequest;
import com.example.paymentservice.dto.response.PaymentMethodResponse;
import com.example.paymentservice.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments/methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping("/users/{userId}")
    public List<PaymentMethodResponse> list(@PathVariable String userId) {
        return paymentMethodService.listByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodResponse create(@Valid @RequestBody PaymentMethodRequest request) {
        return paymentMethodService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        paymentMethodService.delete(id);
    }
}
