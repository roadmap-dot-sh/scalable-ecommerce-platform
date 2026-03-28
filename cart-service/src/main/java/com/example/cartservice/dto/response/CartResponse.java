package com.example.cartservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String cartId;
    private String userId;
    private String couponCode;
    private List<CartItemResponse> items;
    private BigDecimal subtotal;
    private int itemCount;
}
