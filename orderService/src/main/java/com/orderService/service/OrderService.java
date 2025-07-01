package com.orderService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orderService.model.Order;
import com.orderService.model.OrderItem;
import com.orderService.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final InventoryClient inventoryClient;

    public List<Order> getOrdersForUser(Long userId) {
        return repository.findByUserId(userId);
    }
    public Mono<Order> placeOrder(Long userId, List<OrderItem> items) {
        // Check stock availability for all items
        return Mono.just(items)
                .flatMapMany(Flux::fromIterable)
                .flatMap(item -> inventoryClient.isStockAvailable(item.getProductId(), item.getQuantity()))
                .all(available -> available)
                .flatMap(allAvailable -> {
                    if (!allAvailable) {
                        return Mono.error(new RuntimeException("Insufficient stock for one or more products."));
                    }
                    // Deduct stock for all items
                    return Flux.fromIterable(items)
                            .flatMap(item -> inventoryClient.deductStock(item.getProductId(), item.getQuantity()))
                            .all(success -> success)
                            .flatMap(deductSuccess -> {
                                if (!deductSuccess) {
                                    return Mono.error(new RuntimeException("Failed to deduct stock."));
                                }
                                // Save the order if stock deducted successfully
                                double totalAmount = items.stream()
                                        .mapToDouble(i -> i.getPrice() * i.getQuantity())
                                        .sum();

                                Order order = new Order();
                                order.setUserId(userId);
                                order.setOrderDate(LocalDateTime.now());
                                order.setItems(items);
                                order.setTotalAmount(totalAmount);

                                return Mono.just(repository.save(order));
                            });
                });
    }
}
