package com.example.cartservice.controller;

import com.example.cartservice.dto.request.UpdateCartItemRequest;
import com.example.cartservice.dto.response.CartItemResponse;
import com.example.cartservice.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/users/{userId}/items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PatchMapping("/{itemId}")
    public CartItemResponse update(
            @PathVariable String userId,
            @PathVariable String itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return cartItemService.updateItem(userId, itemId, request);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String userId, @PathVariable String itemId) {
        cartItemService.removeItem(userId, itemId);
    }
}
