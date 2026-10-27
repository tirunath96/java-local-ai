package com.tiru.local_ai.payments.controller;

import com.tiru.local_ai.payments.entity.PaymentGateway;
import com.tiru.local_ai.payments.entity.PaymentTransaction;
import com.tiru.local_ai.payments.repository.PaymentGatewayRepository;
import com.tiru.local_ai.payments.repository.PaymentTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentToolsTest {

    private PaymentTransactionRepository transactionRepository;
    private PaymentGatewayRepository gatewayRepository;
    private PaymentTools paymentTools;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(PaymentTransactionRepository.class);
        gatewayRepository = mock(PaymentGatewayRepository.class);

        paymentTools = new PaymentTools(
                transactionRepository, gatewayRepository);
    }

    @Test
    void shouldReturnTransactionWhenFound() {
        PaymentTransaction transaction = new PaymentTransaction(
                "TXN1001",
                new BigDecimal("5000.00"),
                "FAILED",
                "DEBITED");

        when(transactionRepository.findById("TXN1001"))
                .thenReturn(Optional.of(transaction));

        String result = paymentTools.getPaymentStatus("TXN1001");

        assertTrue(result.contains("TXN1001"));
        assertTrue(result.contains("FAILED"));
        verify(transactionRepository).findById("TXN1001");
    }

    @Test
    void shouldReturnMessageWhenTransactionNotFound() {
        when(transactionRepository.findById("TXN9999"))
                .thenReturn(Optional.empty());

        String result = paymentTools.getPaymentStatus("TXN9999");

        assertTrue(result.contains("No transaction found"));
    }

    @Test
    void shouldReturnGatewayWhenFound() {
        PaymentGateway gateway = new PaymentGateway(
                "TXN1001", "TIMEOUT", "GW-78901");

        when(gatewayRepository.findById("TXN1001"))
                .thenReturn(Optional.of(gateway));

        String result = paymentTools.getGatewayStatus("TXN1001");

        assertTrue(result.contains("TIMEOUT"));
        assertTrue(result.contains("GW-78901"));
    }

    @Test
    void shouldReturnMessageWhenGatewayNotFound() {
        when(gatewayRepository.findById("TXN9999"))
                .thenReturn(Optional.empty());

        String result = paymentTools.getGatewayStatus("TXN9999");

        assertTrue(result.contains("No gateway response found"));
    }
}