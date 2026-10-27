package com.tiru.local_ai.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiru.local_ai.payments.entity.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {
    // Additional query methods can be defined here if needed
    
}
