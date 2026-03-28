/*
 * PaymentEvent.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.event;

import com.example.sharedlibrary.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * PaymentEvent.java
 *
 * @author Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEvent {
    String eventId;
    String eventType;
    String paymentId;
    String orderId;
    String userId;
    BigDecimal amount;
    PaymentStatus status;
}
