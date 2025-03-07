package com.prabhash.megacity.service;

import com.prabhash.megacity.entity.Payment;

import java.util.List;

public interface PaymentService {
    void processPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    void updatePaymentStatus(int paymentId, String status);
    void deletePayment(int id);
}
