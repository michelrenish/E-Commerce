package com.productService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productService.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
