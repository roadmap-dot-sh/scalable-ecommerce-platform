package com.example.orderservice.service;

import com.example.orderservice.dto.request.UpdateOrderStatusRequest;
import com.example.orderservice.dto.response.OrderStatusResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.InvalidOrderStatusException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderStatusResponse updateStatus(String orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.COMPLETED && request.getStatus() != OrderStatus.COMPLETED) {
            throw new InvalidOrderStatusException("Cannot change status of completed order");
        }

        order.setStatus(request.getStatus());
        orderRepository.save(order);

        return OrderStatusResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .build();
    }
}
