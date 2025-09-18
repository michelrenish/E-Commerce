package com.inventoryService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
	
	    private Long productId;
	    private String productName;
	    private int quantity;
}
