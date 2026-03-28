/*
 * FallbackHandler.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.apigateway.handler;

import com.example.sharedlibrary.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * FallbackHandler.java
 *
 * @author Nguyen
 */
@Component
public class FallbackHandler {
    public Mono<ServerResponse> handleUserFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ApiResponse.error("User service is temporarily unavailable")
                ));
    }

    public Mono<ServerResponse> handleProductFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ApiResponse.error("Product service is temporarily unavailable")
                ));
    }

    public Mono<ServerResponse> handleCartFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ApiResponse.error("Cart service is temporarily unavailable")
                ));
    }

    public Mono<ServerResponse> handleOrderFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ApiResponse.error("Order service is temporarily unavailable")
                ));
    }

    public Mono<ServerResponse> handlePaymentFallback(ServerRequest request) {
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ApiResponse.error("Payment service is temporarily unavailable")
                ));
    }
}
