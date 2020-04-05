package ru.itis.services.interfaces;

import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;

public interface BookingService {
    BookingDto bookSeats(BookingForm booking);

    void bookPlace(int placeId, long userId);

}
