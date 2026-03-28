package com.example.cartservice.client;

import com.example.cartservice.client.dto.ProductSnapshot;
import com.example.cartservice.client.fallback.ProductServiceFallback;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {

    private final WebClient webClient;

    @Value("${services.product.url:http://localhost:8082}")
    private String productServiceUrl;

    public ProductSnapshot getProduct(String productId) {
        try {
            return webClient.get()
                    .uri(productServiceUrl + "/api/products/{id}", productId)
                    .retrieve()
                    .bodyToMono(ProductSnapshot.class)
                    .block(Duration.ofSeconds(5));
        } catch (WebClientResponseException.NotFound e) {
            return ProductServiceFallback.notFound(productId);
        } catch (Exception e) {
            return ProductServiceFallback.unavailable(productId, e);
        }
    }
}
