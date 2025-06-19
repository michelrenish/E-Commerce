package com.paymentService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentService.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
}

