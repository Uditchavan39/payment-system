package com.example.user.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.user.dto.CreditRequest;
import com.example.user.dto.DebitRequest;

@Service
public class PaymentConsumer {
    private UserService userService;

    public PaymentConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "debit-topic", groupId = "payment-group-v1")
    public void consumeDebit(DebitRequest debitRequest) {
        userService.debit(debitRequest.getUserId(), debitRequest.getAmount());
    }

    // @KafkaListener(topics = "credit-topic", groupId = "payment-group-v2")
    // public void consumeCredit(CreditRequest creditRequest) {
    // userService.credit(creditRequest.getUserId(), creditRequest.getAmount());
    // }
}
