package com.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String shippingName;

    @NotBlank
    private String shippingLine1;

    @NotBlank
    private String shippingCity;

    @NotBlank
    private String shippingPostalCode;

    private String shippingCountry;

    private String paymentProvider;
}
