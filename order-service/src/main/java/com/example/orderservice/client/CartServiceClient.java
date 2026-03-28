package com.example.orderservice.client;

import com.example.orderservice.client.dto.CartClientDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CartServiceClient {

    private final WebClient webClient;

    @Value("${services.cart.url:http://localhost:8083}")
    private String cartBaseUrl;

    public CartClientDtos.CartResponse getCart(String userId) {
        try {
            return webClient.get()
                    .uri(cartBaseUrl + "/api/cart/users/{userId}", userId)
                    .retrieve()
                    .bodyToMono(CartClientDtos.CartResponse.class)
                    .block(Duration.ofSeconds(15));
        } catch (WebClientResponseException e) {
            throw new IllegalStateException("Cart service error: " + e.getStatusCode(), e);
        }
    }

    public void clearCart(String userId) {
        try {
            webClient.delete()
                    .uri(cartBaseUrl + "/api/cart/users/{userId}", userId)
                    .retrieve()
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(15));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to clear cart for user " + userId, e);
        }
    }
}
