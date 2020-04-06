package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.RoomsRepository;
import ru.itis.models.Booking;
import ru.itis.models.SeatStatus;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final RoomsRepository roomsRepository;

    public BookingRepositoryImpl(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void book(Booking booking) {
        entityManager.persist(booking);
        booking.getRooms().forEach(roomsRepository::save);
    }

    @Override
    public void removeBooking(Booking booking) {
        entityManager.remove(booking);
        roomsRepository.deleteSeats(booking.getRooms());
    }

    @Override
    public void removeOverdue(Date date) {
        List<Booking> resultList = entityManager
                .createQuery("select booking from Booking booking where booking.endTime < ?1")
                .setParameter(1, date)
                .getResultList();

        resultList.forEach(booking -> booking.getRooms().forEach(room -> room.setStatus(SeatStatus.FREE)));
        System.out.println(resultList);

        entityManager
                .createQuery("DELETE from Booking where endTime < ?1")
                .setParameter(1, date)
                .executeUpdate();

    }

    @Override
    public List<Booking> getUsersBookings(User user) {
        return (List<Booking>) entityManager
               .createQuery("select booking FROM Booking booking where booking.user = ?1")
               .setParameter(1, user)
               .getResultList();
    }
}
