package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.SeatsRepository;
import ru.itis.models.Booking;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final SeatsRepository seatsRepository;

    public BookingRepositoryImpl(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @Override
    public void book(Booking booking) {
        entityManager.persist(booking);
        booking.getSeats().forEach(seatsRepository::save);
    }
}
