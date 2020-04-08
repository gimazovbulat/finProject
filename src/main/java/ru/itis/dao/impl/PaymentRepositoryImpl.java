package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.PaymentRepository;
import ru.itis.models.Payment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Payment payment) {
        if (payment.getId() == null || payment.getId() < 0) {
            entityManager.persist(payment);
        } else {
            entityManager.merge(payment);
        }
    }
}
