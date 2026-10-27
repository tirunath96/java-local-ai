package com.tiru.local_ai.payments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_gateways")
public class PaymentGateway {

@Id
@Column(name = "transaction_id", nullable = false, unique = true)
private String transactionId;

@Column(name = "gateway_response", nullable = false)
private String gatewayResponse;

@Column(name = "gateway_reference")
private String gatewayReference;

public PaymentGateway() {
}

public PaymentGateway(
        String transactionId,
        String gatewayResponse,
        String gatewayReference) {
    this.transactionId = transactionId;
    this.gatewayResponse = gatewayResponse;
    this.gatewayReference = gatewayReference;
}

public String getTransactionId() {
    return transactionId;
}

public String getGatewayResponse() {
    return gatewayResponse;
}

public String getGatewayReference() {
    return gatewayReference;
}

}