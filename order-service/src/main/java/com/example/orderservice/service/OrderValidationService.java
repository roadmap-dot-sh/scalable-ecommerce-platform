package com.example.orderservice.service;

import com.example.orderservice.client.dto.CartClientDtos;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderValidationService {

    public void assertCartHasItems(CartClientDtos.CartResponse cart) {
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        if (cart.getSubtotal() == null || cart.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cart total must be positive");
        }
    }
}
