package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentMethodRequest {

    @NotBlank
    private String userId;

    private String brand;
    private String last4;
    private String provider;
    private boolean defaultMethod;
}
