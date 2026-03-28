/*
 * ProductRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ProductRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findBySku(String sku);

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategory_IdAndActiveTrue(String categoryId, Pageable pageable);

    Page<Product> findByBrandAndActiveTrue(String brand, Pageable pageable);

    @Query("{ 'active': true, 'price': { $gte: ?0, $lte: ?1 } }")
    Page<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @Query("{ 'active': true, 'tags': { $in: ?0 } }")
    Page<Product> findByTags(List<String> tags, Pageable pageable);

    @Query("{ 'active': true, 'name': { $regex: ?0, $options: 'i' } }")
    Page<Product> searchByName(String namePattern, Pageable pageable);

    List<Product> findByFeaturedTrueAndActiveTrue(Pageable pageable);

    @Query("{ 'active': true, 'availableDate': { $lte: ?0 } }")
    List<Product> findNewArrivals(LocalDateTime date, Pageable pageable);

    @Query(value = "{ 'active': true }", count = true)
    long countActiveProducts();
}
