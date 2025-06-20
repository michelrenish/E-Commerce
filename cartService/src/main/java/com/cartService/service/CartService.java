package com.cartService.service;

import org.springframework.stereotype.Service;

import com.cartService.model.Cart;
import com.cartService.model.CartItem;
import com.cartService.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository repository;

    public Cart getCartByUserId(Long userId) {
        return repository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            return repository.save(cart);
        });
    }

    public Cart addToCart(Long userId, CartItem item) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().add(item);
        return repository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        repository.save(cart);
    }

}
