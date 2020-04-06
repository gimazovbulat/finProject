package ru.itis.services.interfaces;

import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;

import java.util.Date;
import java.util.List;

public interface BookingService {
    BookingDto bookRooms(BookingForm booking);

    void bookPlace(int placeId, long userId);
}
