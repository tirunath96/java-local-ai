package com.tiru.local_ai.payments.repository;

import com.tiru.local_ai.payments.entity.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentGatewayRepository
extends JpaRepository<PaymentGateway, String> {
}