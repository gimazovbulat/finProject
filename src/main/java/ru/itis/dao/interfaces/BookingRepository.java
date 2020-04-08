package ru.itis.dao.interfaces;

import ru.itis.models.Booking;
import ru.itis.models.User;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends CrudRepository<Long, Booking>{
    void removeOverdue(Date date);

    List<Booking> getUsersBookings(User user);
}
