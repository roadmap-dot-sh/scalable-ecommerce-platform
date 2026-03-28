/*
 * AuthenticationFilter.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

/**
 * AuthenticationFilter.java
 *
 * @author Nguyen
 */
@Component
public class AuthenticationFilter implements GlobalFilter {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final List<String> openApiEndpoints = List.of(
            "/api/users/register",
            "/api/user/login"
    );

    private Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isSecured.test(request)) {
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtSecret.getBytes())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    exchange = exchange.mutate()
                            .request(r -> r.header("X-User-Id", claims.getSubject()))
                            .build();
                } catch (Exception e) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
        }

        return chain.filter(exchange);
    }
}
