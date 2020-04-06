package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.SeatsRepository;
import ru.itis.models.Booking;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

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

    @Override
    public void removeBooking(Booking booking) {
        entityManager.remove(booking);
        seatsRepository.deleteSeats(booking.getSeats());
    }

    @Override
    public void removeOverdue(Date date) {
        entityManager
                .createQuery("DELETE from Booking where endTime < ?1")
                .setParameter(1, date);
    }

    @Override
    public List<Booking> getUsersBookings(User user) {
        return (List<Booking>) entityManager
               .createQuery("select booking FROM Booking booking where booking.user = ?1")
               .setParameter(1, user)
               .getResultList();
    }
}
