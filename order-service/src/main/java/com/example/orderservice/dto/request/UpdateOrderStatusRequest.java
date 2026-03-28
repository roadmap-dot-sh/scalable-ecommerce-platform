package com.example.orderservice.dto.request;

import com.example.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    @NotNull
    private OrderStatus status;
}
