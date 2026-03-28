package com.example.productservice.util;

import com.example.productservice.dto.request.CreateProductRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    public void validateCreate(CreateProductRequest request) {
        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be zero or positive");
        }
    }
}
