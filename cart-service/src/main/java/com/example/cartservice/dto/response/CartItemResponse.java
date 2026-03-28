package com.example.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private String id;
    private String productId;
    private String sku;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;
    private String thumbnail;
}
