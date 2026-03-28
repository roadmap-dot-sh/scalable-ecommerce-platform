package com.example.cartservice.service;

import com.example.cartservice.dto.request.UpdateCartItemRequest;
import com.example.cartservice.dto.response.CartItemResponse;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.exception.CartItemNotFoundException;
import com.example.cartservice.mapper.CartMapper;
import com.example.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CartValidationService cartValidationService;
    private final CartMapper cartMapper;

    @Transactional
    public CartItemResponse updateItem(String userId, String itemId, UpdateCartItemRequest request) {
        Cart cart = cartService.getCartEntity(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> itemId.equals(i.getId()))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Item not in cart: " + itemId));
        cartValidationService.validateQuantity(item.getProductId(), request.getQuantity());
        item.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toItem(item);
    }

    @Transactional
    public void removeItem(String userId, String itemId) {
        Cart cart = cartService.getCartEntity(userId);
        boolean removed = cart.getItems().removeIf(i -> itemId.equals(i.getId()));
        if (!removed) {
            throw new CartItemNotFoundException("Item not in cart: " + itemId);
        }
        cartRepository.save(cart);
    }
}
