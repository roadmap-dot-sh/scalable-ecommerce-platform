package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import com.example.orderservice.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
    Optional<Order> findDetailedById(@Param("id") String id);

    Page<Order> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    long countByUserIdAndStatus(String userId, OrderStatus status);
}
