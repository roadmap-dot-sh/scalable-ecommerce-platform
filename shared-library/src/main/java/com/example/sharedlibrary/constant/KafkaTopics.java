/*
 * KafkaTopics.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.constant;

/**
 * KafkaTopics.java
 *
 * @author Nguyen
 */
public final class KafkaTopics {
    public static final String ORDERS_TOPIC = "order-events";
    public static final String PAYMENT_TOPIC = "payment-events";
    public static final String USER_TOPIC = "user-events";
    public static final String PRODUCT_TOPIC = "product-events";
    public static final String NOTIFICATION_TOPIC = "notification-events";
    public static final String INVENTORY_TOPIC = "inventory-events";

    private KafkaTopics() {
    }
}
