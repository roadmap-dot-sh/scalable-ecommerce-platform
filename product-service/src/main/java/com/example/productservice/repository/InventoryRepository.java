/*
 * InventoryRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.productservice.repository;

import com.example.productservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * InventoryRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
}
