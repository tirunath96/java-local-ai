package com.tiru.local_ai.service;

import com.tiru.local_ai.payments.controller.PaymentTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AIService {

    private final ChatClient chatClient;
    private final PaymentTools paymentTools;

    public AIService(
            ChatClient.Builder chatClientBuilder,
            PaymentTools paymentTools) {

        this.chatClient = chatClientBuilder.build();
        this.paymentTools = paymentTools;
    }

    public String askAI(String question) {

if (question == null || question.isBlank()) {
    return "Please provide a transaction ID, for example TXN1001.";
}

Matcher matcher = Pattern
        .compile("(?i)\\bTXN\\d+\\b")
        .matcher(question);

if (!matcher.find()) {
    return "Please provide a valid transaction ID, "
            + "for example TXN1001.";
}

String transactionId = matcher.group().toUpperCase();

try {
    String transactionDetails =
            paymentTools.getPaymentStatus(transactionId);

    String gatewayDetails =
            paymentTools.getGatewayStatus(transactionId);

    if (transactionDetails.startsWith("No transaction found")) {
        return "Transaction " + transactionId
                + " was not found. No investigation was performed.";
    }

    if (gatewayDetails.startsWith("No gateway response found")) {
        return "Gateway details for transaction " + transactionId
                + " were not found. Please investigate further.";
    }

    return chatClient.prompt()
            .system("""
                    You are a banking payment investigation assistant.

                    Analyze only the supplied bank and gateway evidence.
                    Explain the transaction status, debit status,
                    gateway response, discrepancies, and recommended
                    next steps. Clearly identify uncertainty.
                    Never invent transaction information.
                    Never claim that a payment or refund was executed.
                    """)
            .user("""
                    Investigate transaction: %s

                    BANK TRANSACTION DETAILS:
                    %s

                    PAYMENT GATEWAY DETAILS:
                    %s
                    """.formatted(
                    transactionId,
                    transactionDetails,
                    gatewayDetails))
            .call()
            .content();

} catch (Exception e) {
    System.err.println(
            "Payment investigation failed for " + transactionId
                    + ": " + e.getMessage());

    return "Unable to complete the investigation for transaction "
            + transactionId + " due to a system error. Please try again.";
}

}
}