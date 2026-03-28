package com.example.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String id;
    private String slug;
    private String name;
    private String description;
    private String imageUrl;
    private String parentId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
