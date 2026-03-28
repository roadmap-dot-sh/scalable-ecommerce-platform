package com.example.productservice.controller;

import com.example.productservice.dto.request.CreateCategoryRequest;
import com.example.productservice.dto.response.CategoryResponse;
import com.example.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return categoryService.listActive();
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable String id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable String id, @Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }
}
