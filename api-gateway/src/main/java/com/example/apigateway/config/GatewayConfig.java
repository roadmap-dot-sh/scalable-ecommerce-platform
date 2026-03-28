/*
 * GatewayConfig.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * GatewayConfig.java
 *
 * @author Nguyen
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("userService")
                                        .setFallbackUri("forward:/fallback/users"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)))
                        .uri("lb://user-service"))
                .route("product-service", r -> r
                        .path("/api/products/**", "/api/categories/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("productService")
                                        .setFallbackUri("forward:/fallback/products")))
                        .uri("lb://product-service"))
                .route("cart-service", r -> r
                        .path("/api/cart/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("cartService")
                                        .setFallbackUri("forward:/fallback/cart")))
                        .uri("lb://cart-service"))
                .route("order-service", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("orderService")
                                        .setFallbackUri("forward:/fallback/orders")))
                        .uri("lb://order-service"))
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("paymentService")
                                        .setFallbackUri("forward:/fallback/payments")))
                        .uri("lb://payment-service"))
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .uri("lb://notification-service"))
                .build();
    }
}
