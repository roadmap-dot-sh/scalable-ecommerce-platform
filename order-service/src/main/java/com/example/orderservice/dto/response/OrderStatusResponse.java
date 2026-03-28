package com.example.orderservice.dto.response;

import com.example.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
    private String orderId;
    private OrderStatus status;
}
