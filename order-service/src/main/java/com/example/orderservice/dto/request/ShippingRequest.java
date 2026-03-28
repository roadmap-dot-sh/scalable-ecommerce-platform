package com.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingRequest {

    @NotBlank
    private String carrier;

    @NotBlank
    private String trackingNumber;
}
