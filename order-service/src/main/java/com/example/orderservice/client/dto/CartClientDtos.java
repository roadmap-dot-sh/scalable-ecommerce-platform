package com.example.orderservice.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartClientDtos {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartResponse {
        private String cartId;
        private String userId;
        private List<CartItem> items;
        private BigDecimal subtotal;
        private int itemCount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartItem {
        private String id;
        private String productId;
        private String sku;
        private String productName;
        private BigDecimal unitPrice;
        private int quantity;
        private BigDecimal lineTotal;
    }
}
