package com.example.orderservice.mapper;

import com.example.orderservice.dto.response.OrderLineResponse;
import com.example.orderservice.dto.response.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .currency(order.getCurrency())
                .shippingName(order.getShippingName())
                .shippingLine1(order.getShippingLine1())
                .shippingCity(order.getShippingCity())
                .shippingPostalCode(order.getShippingPostalCode())
                .shippingCountry(order.getShippingCountry())
                .paymentProvider(order.getPaymentProvider())
                .lines(order.getItems().stream().map(this::toLine).collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderLineResponse toLine(OrderItem item) {
        return OrderLineResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .sku(item.getSku())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
