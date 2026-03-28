package com.example.productservice.service;

import com.example.productservice.dto.response.InventoryResponse;
import com.example.productservice.entity.Inventory;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.repository.InventoryRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryResponse getByProductId(String productId) {
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> syncFromProduct(productId));
        return map(inv);
    }

    @Transactional
    public InventoryResponse adjust(String productId, int delta) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> Inventory.builder()
                        .productId(productId)
                        .sku(productRepository.findById(productId).map(p -> p.getSku()).orElse(""))
                        .quantityAvailable(0)
                        .reservedQuantity(0)
                        .build());
        int next = (inv.getQuantityAvailable() != null ? inv.getQuantityAvailable() : 0) + delta;
        if (next < 0) {
            throw new IllegalArgumentException("Insufficient stock for product " + productId);
        }
        inv.setQuantityAvailable(next);
        inv.setUpdatedAt(LocalDateTime.now());
        return map(inventoryRepository.save(inv));
    }

    private Inventory syncFromProduct(String productId) {
        return productRepository.findById(productId)
                .map(p -> {
                    Inventory inv = Inventory.builder()
                            .productId(p.getId())
                            .sku(p.getSku())
                            .quantityAvailable(p.getQuantity() != null ? p.getQuantity() : 0)
                            .reservedQuantity(0)
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return inventoryRepository.save(inv);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
    }

    private static InventoryResponse map(Inventory inv) {
        return InventoryResponse.builder()
                .id(inv.getId())
                .productId(inv.getProductId())
                .sku(inv.getSku())
                .quantityAvailable(inv.getQuantityAvailable())
                .reservedQuantity(inv.getReservedQuantity())
                .lastRestockAt(inv.getLastRestockAt())
                .updatedAt(inv.getUpdatedAt())
                .build();
    }
}
