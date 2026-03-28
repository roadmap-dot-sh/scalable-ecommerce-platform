package com.example.cartservice.service;

import com.example.cartservice.client.ProductServiceClient;
import com.example.cartservice.client.dto.ProductSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartValidationService {

    private final ProductServiceClient productServiceClient;

    public ProductSnapshot validateAndLoadProduct(String productId) {
        ProductSnapshot p = productServiceClient.getProduct(productId);
        if (p == null || p.getId() == null) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        if (!p.isActive()) {
            throw new IllegalArgumentException("Product is not available: " + productId);
        }
        if (p.getPrice() == null) {
            throw new IllegalArgumentException("Product has no price: " + productId);
        }
        return p;
    }

    public void validateQuantity(String productId, int requestedQty) {
        ProductSnapshot p = validateAndLoadProduct(productId);
        if (p.getQuantity() == null) {
            return;
        }
        if (requestedQty > p.getQuantity()) {
            throw new IllegalArgumentException(
                    "Only " + p.getQuantity() + " items in stock for product " + productId);
        }
    }
}
