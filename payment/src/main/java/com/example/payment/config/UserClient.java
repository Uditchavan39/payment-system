package com.example.payment.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.payment.common.CreditRequest;
import com.example.payment.common.DebitRequest;
import com.example.payment.common.User;

public interface UserClient {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    @PostMapping("/debit")
    String debitUser(DebitRequest debitRequest);

    @PostMapping("/credit")
    String creditUser(CreditRequest creditRequest);

}
