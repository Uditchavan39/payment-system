package com.example.payment.common;

import java.util.List;

import com.example.payment.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Double balance;
    private List<Payment> payment;
}
