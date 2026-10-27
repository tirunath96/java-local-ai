package com.tiru.local_ai.payments.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "payment_transactions")
public class PaymentTransaction {
    
    @Id
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;   

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "account_debit_status", nullable = false)
    private String accountDebitStatus;

    public PaymentTransaction() {
    }

    public PaymentTransaction(String transactionId, BigDecimal amount, String status, String accountDebitStatus) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.accountDebitStatus = accountDebitStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getAccountDebitStatus() {
        return accountDebitStatus;
    }

}
