package com.example.productservice.controller;

import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.service.ElasticsearchService;
import com.example.productservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final ElasticsearchService elasticsearchService;

    @GetMapping("/advanced-search")
    public Page<ProductResponse> advanced(
            ProductSearchRequest criteria,
            @PageableDefault(size = 20) Pageable pageable) {
        return searchService.search(criteria, pageable);
    }

    @GetMapping("/quick-search")
    public Page<ProductResponse> quick(
            @RequestParam String q,
            @PageableDefault(size = 20) Pageable pageable) {
        return elasticsearchService.search(q, pageable);
    }
}
