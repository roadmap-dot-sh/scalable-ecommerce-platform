package com.example.cartservice.service;

import com.example.cartservice.dto.request.AddToCartRequest;
import com.example.cartservice.dto.request.ApplyCouponRequest;
import com.example.cartservice.dto.response.CartResponse;
import com.example.cartservice.client.UserServiceClient;
import com.example.cartservice.client.dto.ProductSnapshot;
import com.example.cartservice.dto.response.CartSummaryResponse;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.exception.CartNotFoundException;
import com.example.cartservice.mapper.CartMapper;
import com.example.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartValidationService cartValidationService;
    private final UserServiceClient userServiceClient;
    private final CartMapper cartMapper;

    @Transactional
    public CartResponse getCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() ->
                cartRepository.save(Cart.builder().userId(userId).build()));
        return cartMapper.toCart(cart);
    }

    @Transactional(readOnly = true)
    public CartSummaryResponse getSummary(String userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toSummary)
                .orElse(CartSummaryResponse.builder()
                        .itemCount(0)
                        .subtotal(java.math.BigDecimal.ZERO)
                        .build());
    }

    @Transactional
    public CartResponse addItem(String userId, AddToCartRequest request) {
        if (!userServiceClient.userExists(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        ProductSnapshot product = cartValidationService.validateAndLoadProduct(request.getProductId());
        cartValidationService.validateQuantity(request.getProductId(), request.getQuantity());

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart c = Cart.builder().userId(userId).build();
            return cartRepository.save(c);
        });

        CartItem existing = cart.getItems().stream()
                .filter(i -> request.getProductId().equals(i.getProductId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            int newQty = existing.getQuantity() + request.getQuantity();
            cartValidationService.validateQuantity(request.getProductId(), newQty);
            existing.setQuantity(newQty);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .sku(product.getSku())
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(request.getQuantity())
                    .thumbnail(product.getThumbnail())
                    .build();
            cart.getItems().add(item);
        }
        cart = cartRepository.save(cart);
        return cartMapper.toCart(cart);
    }

    @Transactional
    public CartResponse applyCoupon(String userId, ApplyCouponRequest request) {
        Cart cart = getCartEntity(userId);
        cart.setCouponCode(request.getCouponCode());
        return cartMapper.toCart(cartRepository.save(cart));
    }

    @Transactional
    public void clear(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("No cart for user " + userId));
        cart.getItems().clear();
        cart.setCouponCode(null);
        cartRepository.save(cart);
    }

    Cart getCartEntity(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("No cart for user " + userId));
    }
}
