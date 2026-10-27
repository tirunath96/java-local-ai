package com.tiru.local_ai.service;

import com.tiru.local_ai.payments.controller.PaymentTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AIServiceTest {

private AIService aiService;
private PaymentTools paymentTools;

@BeforeEach
void setUp() {
    ChatClient.Builder chatClientBuilder = mock(ChatClient.Builder.class);
    paymentTools = mock(PaymentTools.class);

    aiService = new AIService(chatClientBuilder, paymentTools);
}

@Test
void shouldRejectNullQuestion() {
    String result = aiService.askAI(null);

    assertEquals(
            "Please provide a transaction ID, for example TXN1001.",
            result);
}

@Test
void shouldRejectBlankQuestion() {
    String result = aiService.askAI("   ");

    assertEquals(
            "Please provide a transaction ID, for example TXN1001.",
            result);
}

@Test
void shouldRejectQuestionWithoutTransactionId() {
    String result = aiService.askAI("Please investigate my payment");

    assertEquals(
            "Please provide a valid transaction ID, for example TXN1001.",
            result);
}

@Test
void shouldHandleTransactionNotFound() {
    when(paymentTools.getPaymentStatus("TXN9999"))
            .thenReturn("No transaction found for ID: TXN9999");

    String result = aiService.askAI("Check TXN9999");

    assertEquals(
            "Transaction TXN9999 was not found. No investigation was performed.",
            result);
}

@Test
void shouldHandleGatewayDetailsNotFound() {
    when(paymentTools.getPaymentStatus("TXN9999"))
            .thenReturn("Transaction ID: TXN9999, Status: FAILED");

    when(paymentTools.getGatewayStatus("TXN9999"))
            .thenReturn("No gateway response found for transaction ID: TXN9999");

    String result = aiService.askAI("Check TXN9999");

    assertEquals(
            "Gateway details for transaction TXN9999 were not found. Please investigate further.",
            result);
}

@Test
void shouldHandleServiceException() {
    when(paymentTools.getPaymentStatus("TXN9999"))
            .thenThrow(new RuntimeException("Database unavailable"));

    String result = aiService.askAI("Check TXN9999");

    assertEquals(
            "Unable to complete the investigation for transaction TXN9999 due to a system error. Please try again.",
            result);
}

}