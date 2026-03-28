package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.RefundRequest;
import com.example.paymentservice.dto.response.RefundResponse;
import com.example.paymentservice.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RefundResponse refund(@Valid @RequestBody RefundRequest request) {
        return refundService.refund(request);
    }
}
