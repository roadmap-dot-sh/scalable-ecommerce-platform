package com.example.cartservice.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSnapshot {
    private String id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private boolean active;
    private String thumbnail;
}
