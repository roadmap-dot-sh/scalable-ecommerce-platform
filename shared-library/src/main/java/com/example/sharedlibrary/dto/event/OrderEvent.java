/*
 * OrderEvent.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.event;

import com.example.sharedlibrary.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * OrderEvent.java
 *
 * @author Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEvent {
    String eventId;
    String eventType;
    String orderId;
    String userId;
    String orderNumber;
    BigDecimal totalAmount;
    OrderStatus status;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderItemEvent {
        String productId;
        String productName;
        Integer quantity;
        BigDecimal price;
        BigDecimal subtotal;
    }
}
