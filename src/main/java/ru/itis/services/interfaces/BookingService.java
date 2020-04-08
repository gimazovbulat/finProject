package ru.itis.services.interfaces;

import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.models.Booking;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingDto bookRooms(BookingForm booking);

    void bookPlace(int placeId, long userId);

    BookingDto findBooking(Long bookingId);
}
