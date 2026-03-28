package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRequest {

    @NotBlank
    private String paymentId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    private String reason;
}
