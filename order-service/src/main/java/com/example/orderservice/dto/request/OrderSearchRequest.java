package com.example.orderservice.dto.request;

import com.example.orderservice.model.OrderStatus;
import lombok.Data;

@Data
public class OrderSearchRequest {
    private OrderStatus status;
}
