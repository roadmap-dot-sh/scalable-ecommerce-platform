package com.example.productservice.event;

import com.example.productservice.dto.request.InventoryUpdateRequest;
import com.example.productservice.entity.Product;
import com.example.sharedlibrary.dto.event.ProductEvent;
import com.example.sharedlibrary.enums.ProductEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class ProductEventPublisher {

    public void publishProductCreated(Product product) {
        ProductEvent event = baseEvent(product, ProductEventType.CREATED);
        log.info("[product-events] {}", event);
    }

    public void publishProductUpdated(Product product) {
        ProductEvent event = baseEvent(product, ProductEventType.UPDATED);
        log.info("[product-events] {}", event);
    }

    public void publishInventoryUpdated(Product product, InventoryUpdateRequest request) {
        ProductEvent event = ProductEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(ProductEventType.STOCK_UPDATED.name())
                .productEventType(ProductEventType.STOCK_UPDATED)
                .productId(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(request.getQuantity() != null ? request.getQuantity() : product.getQuantity())
                .timestamp(LocalDateTime.now())
                .build();
        log.info("[product-events] {}", event);
    }

    private static ProductEvent baseEvent(Product product, ProductEventType type) {
        return ProductEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(type.name())
                .productEventType(type)
                .productId(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
