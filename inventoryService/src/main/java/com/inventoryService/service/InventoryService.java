package com.inventoryService.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventoryService.dto.InventoryResponse;
import com.inventoryService.model.Inventory;
import com.inventoryService.repository.InventoryRepository;
import com.productService.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
    private final InventoryRepository repository;
    private final RestTemplate restTemplate;
     
    public InventoryResponse getInventoryDetails(Long productId) {
        Inventory inventory = repository.findByProductId(productId).orElse(null);
        if (inventory == null) {
            return null;
        }

        // Call Product Service to fetch product details
        String productServiceUrl = "http://localhost:8082/api/products/" + productId; // product-service URL
        Product product = restTemplate.getForObject(productServiceUrl, Product.class);

        InventoryResponse response = new InventoryResponse();
        response.setProductId(productId);
        response.setQuantity(inventory.getQuantity());
        response.setProductName(product != null ? product.getName() : "Unknown");

        return response;
    }
    
    // Check stock for a product
    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return repository.findById(productId);
    }

    // Add or update stock quantity
    public Inventory addOrUpdateStock(Long productId, int quantity) {
        Inventory inventory = repository.findById(productId)
                .orElse(new Inventory(productId, 0));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        return repository.save(inventory);
    }

    // Deduct stock (e.g. when order placed), return true if success, false if insufficient stock
    public boolean deductStock(Long productId, int quantity) {
        Optional<Inventory> optionalInventory = repository.findById(productId);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            if (inventory.getQuantity() >= quantity) {
                inventory.setQuantity(inventory.getQuantity() - quantity);
                repository.save(inventory);
                return true;
            }
        }
        return false;
    }
}
