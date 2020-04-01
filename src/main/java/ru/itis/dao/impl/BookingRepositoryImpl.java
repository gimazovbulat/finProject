package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.BookinRepository;
import ru.itis.models.Booking;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookingRepositoryImpl implements BookinRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void book(Booking booking) {
        entityManager.persist(booking);
    }
}
