package com.example.productservice.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class InventoryUpdateRequest {

    @Min(0)
    private Integer quantity;

    private String variantId;

    @Min(0)
    private Integer variantQuantity;
}
