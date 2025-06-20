package com.cartService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cartService.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

}
