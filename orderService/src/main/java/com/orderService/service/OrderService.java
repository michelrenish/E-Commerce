package com.orderService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orderService.model.Order;
import com.orderService.model.OrderItem;
import com.orderService.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Order placeOrder(Long userId, List<OrderItem> items) {
        double totalAmount = items.stream()
            .mapToDouble(item -> item.getQuantity() * item.getPrice())
            .sum();

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(items);
        order.setTotalAmount(totalAmount);

        return repository.save(order);
    }

    public List<Order> getOrdersForUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
