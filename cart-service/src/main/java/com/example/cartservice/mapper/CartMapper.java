package com.example.cartservice.mapper;

import com.example.cartservice.dto.response.CartItemResponse;
import com.example.cartservice.dto.response.CartResponse;
import com.example.cartservice.dto.response.CartSummaryResponse;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartItemResponse toItem(CartItem item) {
        BigDecimal line = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .sku(item.getSku())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .lineTotal(line)
                .thumbnail(item.getThumbnail())
                .build();
    }

    public CartResponse toCart(Cart cart) {
        var items = cart.getItems().stream().map(this::toItem).collect(Collectors.toList());
        BigDecimal subtotal = items.stream()
                .map(CartItemResponse::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int count = items.stream().mapToInt(CartItemResponse::getQuantity).sum();
        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(cart.getUserId())
                .couponCode(cart.getCouponCode())
                .items(items)
                .subtotal(subtotal)
                .itemCount(count)
                .build();
    }

    public CartSummaryResponse toSummary(Cart cart) {
        CartResponse full = toCart(cart);
        return CartSummaryResponse.builder()
                .cartId(full.getCartId())
                .itemCount(full.getItemCount())
                .subtotal(full.getSubtotal())
                .build();
    }
}
