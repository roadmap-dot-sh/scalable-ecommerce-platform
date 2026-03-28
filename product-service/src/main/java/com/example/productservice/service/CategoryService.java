package com.example.productservice.service;

import com.example.productservice.dto.request.CreateCategoryRequest;
import com.example.productservice.dto.response.CategoryResponse;
import com.example.productservice.entity.Category;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryEntity(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
    }

    public CategoryResponse getCategory(String id) {
        return mapToResponse(getCategoryEntity(id));
    }

    public List<CategoryResponse> listActive() {
        return categoryRepository.findByActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {
        if (categoryRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new IllegalArgumentException("Slug already exists: " + request.getSlug());
        }
        Category category = Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .parentId(request.getParentId())
                .active(request.isActive())
                .build();
        category = categoryRepository.save(category);
        log.info("Category created {}", category.getId());
        return mapToResponse(category);
    }

    @Transactional
    public CategoryResponse update(String id, CreateCategoryRequest request) {
        Category category = getCategoryEntity(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        category.setParentId(request.getParentId());
        category.setActive(request.isActive());
        if (!category.getSlug().equals(request.getSlug())) {
            categoryRepository.findBySlug(request.getSlug()).ifPresent(c -> {
                if (!c.getId().equals(id)) {
                    throw new IllegalArgumentException("Slug already in use");
                }
            });
            category.setSlug(request.getSlug());
        }
        return mapToResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponse mapToResponse(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .slug(category.getSlug())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .parentId(category.getParentId())
                .active(category.isActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
