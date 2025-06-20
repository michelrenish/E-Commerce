package com.cartService.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartService.model.Cart;
import com.cartService.model.CartItem;
import com.cartService.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	 private final CartService cartService;

	    @GetMapping("/{userId}")
	    public Cart getCart(@PathVariable Long userId) {
	        return cartService.getCartByUserId(userId);
	    }

	    @PostMapping("/{userId}/add")
	    public Cart addItem(@PathVariable Long userId, @RequestBody CartItem item) {
	        return cartService.addToCart(userId, item);
	    }

	    @DeleteMapping("/{userId}/clear")
	    public void clearCart(@PathVariable Long userId) {
	        cartService.clearCart(userId);
	    }

}
