package com.inventoryService.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventoryService.model.Inventory;
import com.inventoryService.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

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
