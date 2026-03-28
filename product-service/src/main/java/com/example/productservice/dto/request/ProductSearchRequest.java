package com.example.productservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSearchRequest {
    private String keyword;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<String> tags;
    private String brand;
}
