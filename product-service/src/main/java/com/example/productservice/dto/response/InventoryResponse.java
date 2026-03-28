package com.example.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private String id;
    private String productId;
    private String sku;
    private Integer quantityAvailable;
    private Integer reservedQuantity;
    private LocalDateTime lastRestockAt;
    private LocalDateTime updatedAt;
}
