/*
 * ProductVariant.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ProductVariant.java
 *
 * @author Nguyen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
    private String id;
    private String sku;
    private String name;
    private Map<String, String> attributes = new HashMap<>();
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private Integer quantity;
    private String image;
    private boolean active = true;
}
