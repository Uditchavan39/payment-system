package com.example.payment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.dto.PaymentRequest;
import com.example.payment.service.PaymentService;

@RestController
@RequestMapping("api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) {
        try {

            paymentService.makePayment(paymentRequest.getSenderId(), paymentRequest.getReceiverId(),
                    paymentRequest.getAmount());
            logger.info("Payment successful: " + paymentRequest);
            return ResponseEntity.ok("Payment successful");
        } catch (Exception e) {
            logger.error("Payment failed: " + e.getMessage());
            return ResponseEntity.status(500).body("Payment failed: " + e.getMessage());
        }

    }
}
