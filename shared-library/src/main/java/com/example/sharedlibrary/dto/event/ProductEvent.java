/*
 * ProductEvent.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.event;

import com.example.sharedlibrary.enums.ProductEventType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ProductEvent.java
 *
 * @author Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEvent {
    String eventId;
    String eventType;
    String productId;
    String sku;
    String name;
    BigDecimal price;
    Integer quantity;
    ProductEventType productEventType;
    LocalDateTime timestamp;
}
