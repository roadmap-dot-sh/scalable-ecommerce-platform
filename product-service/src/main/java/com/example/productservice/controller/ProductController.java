package com.example.productservice.controller;

import com.example.productservice.dto.request.CreateProductRequest;
import com.example.productservice.dto.request.UpdateProductRequest;
import com.example.productservice.dto.response.ProductDetailResponse;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/{id}/detail")
    public ProductDetailResponse getDetail(@PathVariable String id) {
        return productService.getProductDetail(id);
    }

    @GetMapping("/sku/{sku}")
    public ProductResponse getBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable String id, @Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @GetMapping
    public Page<ProductResponse> list(
            @PageableDefault(size = 20) Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductResponse> byCategory(
            @PathVariable String categoryId,
            @PageableDefault(size = 20) Pageable pageable) {
        return productService.getProductsByCategory(categoryId, pageable);
    }

    @GetMapping("/featured")
    public List<ProductResponse> featured() {
        return productService.getFeaturedProducts();
    }
}
