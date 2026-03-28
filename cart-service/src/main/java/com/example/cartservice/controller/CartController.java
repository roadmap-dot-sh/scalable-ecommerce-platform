package com.example.cartservice.controller;

import com.example.cartservice.dto.request.AddToCartRequest;
import com.example.cartservice.dto.request.ApplyCouponRequest;
import com.example.cartservice.dto.response.CartResponse;
import com.example.cartservice.dto.response.CartSummaryResponse;
import com.example.cartservice.service.CartService;
import com.example.cartservice.service.CartSyncService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartSyncService cartSyncService;

    @GetMapping("/users/{userId}")
    public CartResponse getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @GetMapping("/users/{userId}/summary")
    public CartSummaryResponse summary(@PathVariable String userId) {
        return cartService.getSummary(userId);
    }

    @PostMapping("/users/{userId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addItem(
            @PathVariable String userId,
            @Valid @RequestBody AddToCartRequest request) {
        return cartService.addItem(userId, request);
    }

    @PostMapping("/users/{userId}/coupon")
    public CartResponse applyCoupon(
            @PathVariable String userId,
            @Valid @RequestBody ApplyCouponRequest request) {
        return cartService.applyCoupon(userId, request);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear(@PathVariable String userId) {
        cartService.clear(userId);
    }

    @PostMapping("/merge")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void merge(
            @RequestParam String anonymousId,
            @RequestParam String userId) {
        cartSyncService.mergeAnonymousIntoUser(anonymousId, userId);
    }
}
