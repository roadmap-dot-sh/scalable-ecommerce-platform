package com.example.productservice.service;

import com.example.productservice.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Facade for product search. Backed by Mongo today; swap for Elasticsearch later.
 */
@Service
@RequiredArgsConstructor
public class ElasticsearchService {

    private final ProductService productService;

    public Page<ProductResponse> search(String query, Pageable pageable) {
        return productService.searchProducts(query, pageable);
    }
}
