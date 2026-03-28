package com.example.orderservice.model;

public enum OrderStatus {
    AWAITING_PAYMENT,
    PAID,
    PAYMENT_FAILED,
    CANCELLED,
    SHIPPED,
    COMPLETED
}
