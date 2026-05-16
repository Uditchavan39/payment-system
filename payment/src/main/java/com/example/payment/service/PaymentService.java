package com.example.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.payment.common.CreditRequest;
import com.example.payment.common.DebitRequest;
import com.example.payment.common.User;
import com.example.payment.config.UserClient;
import com.example.payment.model.Payment;
import com.example.payment.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    KafkaTemplate<String, CreditRequest> kafkaTemplate;
    @Autowired
    KafkaTemplate<String, DebitRequest> dkafkaTemplate;

    private static final String DEBIT_TOPIC = "debit-topic";

    private static final String CREDIT_TOPIC = "credit-topic";

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(value = TxType.REQUIRES_NEW)
    public void makePayment(Long senderId, Long receiverId, Double amount) {
        dkafkaTemplate.send(DEBIT_TOPIC, new DebitRequest(senderId, amount)).whenComplete((result, ex) -> {
            if (ex != null) {
                logger.error("Failed to send debit request for user " + senderId + ": " + ex.getMessage());
            } else {
                logger.info("Debit request sent for user " + senderId);
            }
        });
        kafkaTemplate.send(CREDIT_TOPIC, new CreditRequest(receiverId, amount)).whenComplete((result, ex) -> {
            if (ex == null)
                logger.info("Credited Successfully " + receiverId);
            else
                logger.error("Failed to send credit request for user " + receiverId + ": " + ex.getMessage());
        });
        // Create and save payment record
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setSenderId(senderId);
        payment.setReceiverId(receiverId);
        paymentRepository.save(payment);
    }

}
