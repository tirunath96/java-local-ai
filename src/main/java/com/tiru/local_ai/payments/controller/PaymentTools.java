package com.tiru.local_ai.payments.controller;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import org.springframework.ai.mcp.annotation.McpTool;

import com.tiru.local_ai.payments.entity.PaymentGateway;
import com.tiru.local_ai.payments.entity.PaymentTransaction;
import com.tiru.local_ai.payments.repository.PaymentGatewayRepository;
import com.tiru.local_ai.payments.repository.PaymentTransactionRepository;

@Component
public class PaymentTools {

    private final PaymentTransactionRepository repository;
    private final PaymentGatewayRepository gatewayRepository;

    public PaymentTools(
            PaymentTransactionRepository repository,
            PaymentGatewayRepository gatewayRepository) {
        this.repository = repository;
        this.gatewayRepository = gatewayRepository;
    }

    //@Tool(description = "Look up a payment transaction by transaction ID")
    @McpTool(description = "Look up a payment transaction by transaction ID")
    public String getPaymentStatus(String transactionId) {

        if (transactionId == null || transactionId.isBlank()) {
            return "A valid transaction ID is required.";
        }

        return repository.findById(transactionId.trim().toUpperCase())
                .map(transaction -> """
                        Transaction ID: %s
                        Amount: INR %s
                        Transaction status: %s
                        Account debit status: %s
                        """.formatted(
                        transaction.getTransactionId(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getAccountDebitStatus()))
                .orElse("No transaction found for ID: " + transactionId);
    }

    //@Tool(description = "Look up payment gateway details by transaction ID")
    @McpTool(description = "Look up payment gateway details by transaction ID")
    public String getGatewayStatus(String transactionId) {

        if (transactionId == null || transactionId.isBlank()) {
            return "A valid transaction ID is required.";
        }

        return gatewayRepository.findById(transactionId.trim().toUpperCase())
                .map(gateway -> """
                        Transaction ID: %s
                        Gateway response: %s
                        Gateway reference: %s
                        """.formatted(
                        gateway.getTransactionId(),
                        gateway.getGatewayResponse(),
                        gateway.getGatewayReference()))
                .orElse("No gateway response found for transaction ID: "
                        + transactionId);
    }
}