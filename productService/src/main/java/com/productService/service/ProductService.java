package com.productService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.productService.model.Product;
import com.productService.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
