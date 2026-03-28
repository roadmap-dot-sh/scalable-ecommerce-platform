package com.example.cartservice.repository;

import com.example.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @EntityGraph(attributePaths = {"items"})
    Optional<Cart> findByUserId(String userId);
}
