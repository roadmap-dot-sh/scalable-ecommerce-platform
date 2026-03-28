package com.example.productservice.service;

import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductService productService;

    public Page<ProductResponse> search(ProductSearchRequest criteria, Pageable pageable) {
        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            return productService.searchProducts(criteria.getKeyword(), pageable);
        }
        return productService.filterProducts(
                criteria.getMinPrice(),
                criteria.getMaxPrice(),
                criteria.getTags(),
                criteria.getBrand(),
                pageable);
    }
}
