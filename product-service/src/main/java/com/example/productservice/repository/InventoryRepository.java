package com.example.productservice.repository;

import com.example.productservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {

    Optional<Inventory> findByProductId(String productId);

    Optional<Inventory> findBySku(String sku);
}
