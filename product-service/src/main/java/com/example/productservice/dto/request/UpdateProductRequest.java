package com.example.productservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private List<String> images;
    private String thumbnail;
    private String categoryId;
    private Set<String> tags;
    private String brand;
    private Boolean featured;
    private Boolean active;
}
