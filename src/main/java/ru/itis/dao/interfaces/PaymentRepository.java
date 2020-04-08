package ru.itis.dao.interfaces;

import ru.itis.models.Payment;

public interface PaymentRepository {
    void save(Payment payment);
}
