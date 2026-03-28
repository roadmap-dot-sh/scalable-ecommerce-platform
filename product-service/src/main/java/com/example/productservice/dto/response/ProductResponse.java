package com.example.productservice.dto.response;

import com.example.productservice.entity.ProductVariant;
import com.example.productservice.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String sku;
    private String name;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private List<String> images;
    private String thumbnail;
    private CategoryResponse category;
    private Set<String> tags;
    private String brand;
    private String manufacturer;
    private String modelNumber;
    private Double averageRating;
    private Integer reviewCount;
    private boolean active;
    private boolean featured;
    private List<ProductVariant> variants;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
