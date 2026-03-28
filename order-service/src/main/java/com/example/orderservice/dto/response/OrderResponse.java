package com.example.orderservice.dto.response;

import com.example.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private String userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String currency;
    private String shippingName;
    private String shippingLine1;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;
    private String paymentProvider;
    private List<OrderLineResponse> lines;
    private LocalDateTime createdAt;
}
