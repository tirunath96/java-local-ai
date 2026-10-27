package com.tiru.local_ai.payments.repository;

import com.tiru.local_ai.payments.entity.PaymentTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentRepositoryIntegrationTest {

    @Autowired
    private PaymentTransactionRepository repository;

    @Test
    void shouldSaveAndRetrieveTransaction() {

        PaymentTransaction transaction = new PaymentTransaction(
                "TEST1001",
                new BigDecimal("1500.00"),
                "FAILED",
                "DEBITED"
        );

        repository.save(transaction);

        PaymentTransaction result =
                repository.findById("TEST1001").orElseThrow();

        assertEquals("FAILED", result.getStatus());
        assertEquals("DEBITED", result.getAccountDebitStatus());
        assertEquals(0,
                new BigDecimal("1500.00")
                        .compareTo(result.getAmount()));
    }
}