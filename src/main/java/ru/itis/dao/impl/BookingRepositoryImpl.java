package ru.itis.dao.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.RoomsRepository;
import ru.itis.models.Booking;
import ru.itis.models.RoomStatus;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final RoomsRepository roomsRepository;

    public BookingRepositoryImpl(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public Long save(Booking booking) {
        entityManager.persist(booking);
        booking.getRooms().forEach(roomsRepository::save);
        return booking.getId();
    }

    @Override
    public Optional<Booking> find(Long id) {
        Booking booking = entityManager.find(Booking.class, id);
        return Optional.of(booking);
    }

    @Override
    public void update(Booking booking) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void removeOverdue(LocalDate date) {
        List<Booking> overdueBookings = entityManager
                .createQuery("select booking from Booking booking where booking.endDate < ?1 and booking.paid = ?2")
                .setParameter(1, date)
                .setParameter(2, false)
                .getResultList();

        overdueBookings.forEach(booking -> booking.getRooms().forEach(room -> room.setStatus(RoomStatus.FREE)));

        entityManager
                .createQuery("DELETE from Booking where endDate < ?1")
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

    @Override
    public void resetRooms(LocalDate date) { //мб какой-нить батч сейв сделать
        List<Booking> oldBookings = entityManager
                .createQuery("select booking from Booking booking where booking.endDate < ?1")
                .setParameter(1, date)
                .getResultList();

        oldBookings.forEach(booking -> booking.getRooms().forEach(room -> {
            room.setStatus(RoomStatus.FREE);
            roomsRepository.save(room);
        }));
    }
}
