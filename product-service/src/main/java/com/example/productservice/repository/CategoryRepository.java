/*
 * CategoryRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.repository;

import com.example.productservice.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * CategoryRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
