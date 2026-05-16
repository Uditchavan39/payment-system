package com.example.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.dto.CreditRequest;
import com.example.user.dto.DebitRequest;
import com.example.user.dto.UserRequest;
import com.example.user.model.User;
import com.example.user.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest.getName(), userRequest.getBalance());
            logger.info("User created with ID: {}", user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("User created with ID: " + user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        try {
            Double balance = userService.getBalance(userId);
            logger.info("Fetched balance for user ID {}: {}", userId, balance);
            return ResponseEntity.ok("User balance: " + balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching balance: " + e.getMessage());
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<?> credit(@RequestBody CreditRequest creditRequest) {
        try {
            Double newBalance = userService.credit(creditRequest.getUserId(), creditRequest.getAmount());
            logger.info("Credited amount {} to user ID {}: New balance {}", creditRequest.getAmount(),
                    creditRequest.getUserId(), newBalance);
            return ResponseEntity.ok("New balance: " + newBalance);
        } catch (Exception e) {
            logger.error("Error adding balance for user ID {}: {}", creditRequest.getUserId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding balance: " + e.getMessage());
        }
    }

    @PostMapping("/debit")
    public ResponseEntity<?> debit(@RequestBody DebitRequest debitRequest) {
        try {
            Double newBalance = userService.debit(debitRequest.getUserId(), debitRequest.getAmount());
            logger.info("Debited amount {} from user ID {}: New balance {}", debitRequest.getAmount(),
                    debitRequest.getUserId(), newBalance);
            return ResponseEntity.ok("New balance: " + newBalance);
        } catch (Exception e) {
            logger.error("Error debiting balance for user ID {}: {}", debitRequest.getUserId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error debiting balance: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            logger.info("Fetched user details for user ID {}: Name: {}, Balance: {}", userId, user.getName(),
                    user.getBalance());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error fetching user details for user ID {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user: " + e.getMessage());
        }
    }

}
