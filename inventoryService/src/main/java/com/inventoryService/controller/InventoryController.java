package com.inventoryService.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryService.model.Inventory;
import com.inventoryService.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService service;

    // Check stock by productId
    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getStock(@PathVariable Long productId) {
        Optional<Inventory> inventory = service.getInventoryByProductId(productId);
        return inventory.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Add or update stock
    @PostMapping("/add")
    public ResponseEntity<Inventory> addStock(@RequestParam Long productId,
                                              @RequestParam int quantity) {
        Inventory updatedInventory = service.addOrUpdateStock(productId, quantity);
        return ResponseEntity.ok(updatedInventory);
    }

    // Deduct stock for order
    @PostMapping("/deduct")
    public ResponseEntity<String> deductStock(@RequestParam Long productId,
                                              @RequestParam int quantity) {
        boolean success = service.deductStock(productId, quantity);
        if (success) {
            return ResponseEntity.ok("Stock deducted successfully");
        } else {
            return ResponseEntity.badRequest().body("Insufficient stock");
        }
    }
}
