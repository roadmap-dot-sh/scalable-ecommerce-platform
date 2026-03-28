package com.example.orderservice.controller;

import com.example.orderservice.dto.request.UpdateOrderStatusRequest;
import com.example.orderservice.dto.response.OrderStatusResponse;
import com.example.orderservice.service.OrderStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @PatchMapping("/{id}/status")
    public OrderStatusResponse updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orderStatusService.updateStatus(id, request);
    }
}
