/*
 * IdGenerator.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IdGenerator.java
 *
 * @author Nguyen
 */
public final class IdGenerator {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + counter.incrementAndGet();
    }

    public static String generatePaymentId() {
        return "PAY-" + System.currentTimeMillis() + "-" + counter.incrementAndGet();
    }

    public static String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + counter.incrementAndGet();
    }

    private IdGenerator() {
    }
}
