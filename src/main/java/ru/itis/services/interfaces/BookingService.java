package ru.itis.services.interfaces;

import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;

public interface BookingService {
    BookingDto bookRooms(BookingForm booking);

    void bookPlace(int placeId, long userId);

    BookingDto findBooking(Long bookingId);

    BookingDto pay(Long bookingId);
}
