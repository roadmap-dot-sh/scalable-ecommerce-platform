package com.example.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineResponse {
    private String id;
    private String productId;
    private String sku;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
}
