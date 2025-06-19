package com.paymentService.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentService.model.Payment;
import com.paymentService.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public Payment pay(
        @RequestParam Long orderId,
        @RequestParam Long userId,
        @RequestParam double amount) {
        return paymentService.makePayment(orderId, userId, amount);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> userPayments(@PathVariable Long userId) {
        return paymentService.getUserPayments(userId);
    }
}
