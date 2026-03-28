package com.example.productservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class CreateProductRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    private String description;
    private String shortDescription;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    private BigDecimal compareAtPrice;
    private BigDecimal cost;

    private List<String> images = new ArrayList<>();
    private String thumbnail;
    private String categoryId;
    private Set<String> tags = new HashSet<>();

    private String brand;
    private String manufacturer;
    private String modelNumber;
    private String upc;
    private String mpn;

    private Integer weight;
    private String weightUnit;

    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;

    private LocalDateTime availableDate;
    private boolean featured;

    @PositiveOrZero
    private Integer quantity = 0;

    @Valid
    private List<ProductVariantRequest> variants = new ArrayList<>();

    @Data
    public static class ProductVariantRequest {
        @NotBlank
        private String sku;
        private String name;
        private Map<String, String> attributes;
        private BigDecimal price;
        private BigDecimal compareAtPrice;
        private Integer quantity;
        private String image;
    }
}
