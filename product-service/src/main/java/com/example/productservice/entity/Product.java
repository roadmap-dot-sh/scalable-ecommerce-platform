/*
 * Product.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Product.java
 *
 * @author Nguyen
 */
@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @Indexed(unique = true)
    private String sku;

    @Indexed
    private String name;

    private String description;

    private String shortDescription;

    private BigDecimal price;

    private BigDecimal compareAtPrice;  // original price for sale

    private BigDecimal cost;    // cost price

    @Builder.Default
    private List<String> images = new ArrayList<>();

    private String thumbnail;

    @DBRef
    private Category category;

    @Builder.Default
    private Set<String> tags = new HashSet<>();

    private String brand;

    private String manufacturer;

    private String modelNumber;

    private String upc; // Universal Product Code

    private String mpn; // Manufacturer Part Number

    private boolean active = true;

    private boolean featured = false;

    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    private Double averageRating;

    private Integer reviewCount;

    private String seoTitle;

    private String seoDescription;

    private String seoKeywords;

    private Integer weight; // in grams

    private String weightUnit;

    @Builder.Default
    private List<ProductVariant> variants = new ArrayList<>();

    @Builder.Default
    private List<String> relatedProducts = new ArrayList<>();

    private LocalDateTime availableDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (reviewCount == null) reviewCount = 0;
        if (averageRating == null) averageRating = 0.0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
