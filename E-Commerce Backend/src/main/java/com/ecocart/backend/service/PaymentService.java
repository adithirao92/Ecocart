package com.ecocart.backend.service;

import com.ecocart.backend.entity.Payment;

public interface PaymentService {
    Payment processPayment(Payment payment);
    Payment getPaymentStatus(Long paymentId);
}
