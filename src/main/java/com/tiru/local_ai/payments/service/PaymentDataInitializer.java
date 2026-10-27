package com.tiru.local_ai.payments.service;

import com.tiru.local_ai.payments.entity.PaymentGateway;
import com.tiru.local_ai.payments.entity.PaymentTransaction;
import com.tiru.local_ai.payments.repository.PaymentGatewayRepository;
import com.tiru.local_ai.payments.repository.PaymentTransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class PaymentDataInitializer {

@Bean
CommandLineRunner initializePayments(
        PaymentTransactionRepository transactionRepository,
        PaymentGatewayRepository gatewayRepository) {

    return args -> {

        if (!transactionRepository.existsById("TXN1001")) {
            transactionRepository.save(new PaymentTransaction(
                    "TXN1001", new BigDecimal("5000.00"),
                    "FAILED", "DEBITED"));

            transactionRepository.save(new PaymentTransaction(
                    "TXN1002", new BigDecimal("2500.00"),
                    "SUCCESS", "DEBITED"));

            transactionRepository.save(new PaymentTransaction(
                    "TXN1003", new BigDecimal("1200.00"),
                    "PENDING", "NOT DEBITED"));
        }

        if (!gatewayRepository.existsById("TXN1001")) {
            gatewayRepository.save(new PaymentGateway(
                    "TXN1001", "TIMEOUT", "GW-78901"));

            gatewayRepository.save(new PaymentGateway(
                    "TXN1002", "APPROVED", "GW-78902"));

            gatewayRepository.save(new PaymentGateway(
                    "TXN1003", "PROCESSING", "GW-78903"));
        }

        System.out.println("Payment and gateway seed data checked.");
    };
}

}