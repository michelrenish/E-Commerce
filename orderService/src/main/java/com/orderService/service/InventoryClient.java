package com.orderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryClient {
	private final WebClient webClient =WebClient.create("http://localhost:8086/api/inventory");
	
	 // Check stock availability for a product
    public Mono<Boolean> isStockAvailable(Long productId, int requiredQuantity) {
        return webClient.get()
                .uri("/{productId}", productId)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .map(inventory -> inventory.getQuantity() >= requiredQuantity)
                .onErrorReturn(false);
    }
    // Deduct stock for product
    public Mono<Boolean> deductStock(Long productId, int quantity) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                    .path("/deduct")
                    .queryParam("productId", productId)
                    .queryParam("quantity", quantity)
                    .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> response.contains("successfully"))
                .onErrorReturn(false);
    }

    // Inner class to map inventory response JSON
    private static class InventoryResponse {
        private Long productId;
        private int quantity;
	        public int getQuantity() {
	            return quantity;
	        }
    }
}
