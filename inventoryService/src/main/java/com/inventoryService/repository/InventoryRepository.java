package com.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventoryService.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}