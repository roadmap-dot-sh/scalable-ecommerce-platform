package com.example.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    private String id;

    @Indexed
    private String productId;

    @Indexed
    private String sku;

    private Integer quantityAvailable;

    private Integer reservedQuantity;

    private LocalDateTime lastRestockAt;

    private LocalDateTime updatedAt;
}
