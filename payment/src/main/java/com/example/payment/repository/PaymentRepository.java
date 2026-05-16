package com.example.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
