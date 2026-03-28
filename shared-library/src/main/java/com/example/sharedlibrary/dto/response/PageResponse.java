/*
 * PageResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * PageResponse.java
 *
 * @author Nguyen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean first;
    boolean last;
}
