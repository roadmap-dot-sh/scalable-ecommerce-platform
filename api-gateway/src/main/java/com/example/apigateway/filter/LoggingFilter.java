/*
 * LoggingFilter.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * LoggingFilter.java
 *
 * @author Nguyen
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(
                        Mono.fromRunnable(() -> {
                            long duration = System.currentTimeMillis() - startTime;
                            log.info("Response: {} - {} ms",
                                    exchange.getResponse().getStatusCode(), duration);
                        })
                );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
