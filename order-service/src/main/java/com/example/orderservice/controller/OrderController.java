package com.example.orderservice.controller;

import com.example.orderservice.dto.request.CreateOrderRequest;
import com.example.orderservice.dto.response.OrderResponse;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/users/{userId}/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse checkout(
            @PathVariable String userId,
            @Valid @RequestBody CreateOrderRequest request) {
        return orderService.checkout(userId, request);
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable String id) {
        return orderService.getById(id);
    }

    @GetMapping("/users/{userId}")
    public Page<OrderResponse> listForUser(
            @PathVariable String userId,
            @PageableDefault(size = 20) Pageable pageable) {
        return orderService.listForUser(userId, pageable);
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(
            @PathVariable String id,
            @RequestParam String userId) {
        return orderService.cancel(id, userId);
    }
}
