/*
 * ProductService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.service;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.InventoryUpdateRequest;
import com.example.productservice.dto.request.UpdateProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.entity.Review;
import com.example.productservice.event.ProductEventPublisher;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductService.java
 *
 * @author Nguyen
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductEventPublisher eventPublisher;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating new product with SKU: {}", request.getSku());

        // Check if SKU already exists
        if (productRepository.findBySku(request.getSku()).isPresent()) {
            throw new RuntimeException("Product with SKU " + request.getSku() + " already exists");
        }

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .price(request.getPrice())
                .compareAtPrice(request.getCompareAtPrice())
                .cost(request.getCost())
                .images(request.getImages())
                .thumbnail(request.getThumbnail())
                .brand(request.getBrand())
                .manufacturer(request.getManufacturer())
                .modelNumber(request.getModelNumber())
                .upc(request.getUpc())
                .mpn(request.getMpn())
                .tags(request.getTags())
                .weight(request.getWeight())
                .weightUnit(request.getWeightUnit())
                .seoTitle(request.getSeoTitle())
                .seoDescription(request.getSeoDescription())
                .seoKeywords(request.getSeoKeywords())
                .availableDate(request.getAvailableDate())
                .featured(request.isFeatured())
                .build();

        // Set category if provided
        if (request.getCategoryId() != null) {
            product.setCategory(categoryService.getCategoryEntity(request.getCategoryId()));
        }

        // Set variants if provided
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            List<ProductVariant> variants = request.getVariants().stream()
                    .map(variantRequest -> ProductVariant.builder()
                            .sku(variantRequest.getSku())
                            .name(variantRequest.getName())
                            .attributes(variantRequest.getAttributes())
                            .price(variantRequest.getPrice())
                            .compareAtPrice(variantRequest.getCompareAtPrice())
                            .quantity(variantRequest.getQuantity())
                            .image(variantRequest.getImage())
                            .active(true)
                            .build())
                    .collect(Collectors.toList());
            product.setVariants(variants);
        }

        product = productRepository.save(product);

        // Publish product created event
        eventPublisher.publishProductCreated(product);

        log.info("Product created successfully with id: {}", product.getId());
        return mapToResponse(product);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProduct(String id) {
        log.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return mapToResponse(product);
    }

    @Cacheable(value = "products", key = "#sku")
    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found with SKU: " + sku));

        return mapToResponse(product);
    }

    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public ProductResponse updateProduct(String id, UpdateProductRequest request) {
        log.info("Updating product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getShortDescription() != null) {
            product.setShortDescription(request.getShortDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getCompareAtPrice() != null) {
            product.setCompareAtPrice(request.getCompareAtPrice());
        }
        if (request.getImages() != null) {
            product.setImages(request.getImages());
        }
        if (request.getThumbnail() != null) {
            product.setThumbnail(request.getThumbnail());
        }
        if (request.getCategoryId() != null) {
            product.setCategory(categoryService.getCategoryEntity(request.getCategoryId()));
        }
        if (request.getTags() != null) {
            product.setTags(request.getTags());
        }
        if (request.getBrand() != null) {
            product.setBrand(request.getBrand());
        }
        if (request.isFeatured() != product.isFeatured()) {
            product.setFeatured(request.isFeatured());
        }
        if (request.isActive() != product.isActive()) {
            product.setActive(request.isActive());
        }

        product = productRepository.save(product);

        // Publish product updated event
        eventPublisher.publishProductUpdated(product);

        return mapToResponse(product);
    }

    @Transactional
    public void updateInventory(String productId, InventoryUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update main product quantity
        if (request.getQuantity() != null) {
            product.setQuantity(request.getQuantity());
        }

        // Update variant inventory if specified
        if (request.getVariantId() != null && request.getVariantQuantity() != null) {
            product.getVariants().stream()
                    .filter(variant -> variant.getId().equals(request.getVariantId()))
                    .findFirst()
                    .ifPresent(variant -> variant.setQuantity(request.getVariantQuantity()));
        }

        productRepository.save(product);

        // Publish inventory updated event
        eventPublisher.publishInventoryUpdated(product, request);
    }

    @Transactional
    public void addReview(String productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        review.setId(java.util.UUID.randomUUID().toString());
        product.getReviews().add(review);

        // Update average rating
        double totalRating = product.getReviews().stream()
                .mapToInt(Review::getRating)
                .sum();
        product.setAverageRating(totalRating / product.getReviews().size());
        product.setReviewCount(product.getReviews().size());

        productRepository.save(product);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> getProductsByCategory(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByName(keyword, pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> filterProducts(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            List<String> tags,
            String brand,
            Pageable pageable) {

        if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceRange(minPrice, maxPrice, pageable)
                    .map(this::mapToResponse);
        } else if (tags != null && !tags.isEmpty()) {
            return productRepository.findByTags(tags, pageable)
                    .map(this::mapToResponse);
        } else if (brand != null) {
            return productRepository.findByBrandAndActiveTrue(brand, pageable)
                    .map(this::mapToResponse);
        }

        return getAllProducts(pageable);
    }

    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findByFeaturedTrueAndActiveTrue(Pageable.ofSize(10))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .images(product.getImages())
                .thumbnail(product.getThumbnail())
                .category(product.getCategory() != null ?
                        categoryService.mapToResponse(product.getCategory()) : null)
                .tags(product.getTags())
                .brand(product.getBrand())
                .manufacturer(product.getManufacturer())
                .modelNumber(product.getModelNumber())
                .averageRating(product.getAverageRating())
                .reviewCount(product.getReviewCount())
                .active(product.isActive())
                .featured(product.isFeatured())
                .variants(product.getVariants())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
