package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.SeatsRepository;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.models.Booking;
import ru.itis.models.Seat;
import ru.itis.models.SeatStatus;
import ru.itis.services.interfaces.BookingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.itis.models.Booking.builder;
import static ru.itis.models.Booking.toBookingDto;
import static ru.itis.models.User.fromUserDto;

@Transactional
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final SeatsRepository seatsRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              SeatsRepository seatsRepository) {
        this.bookingRepository = bookingRepository;
        this.seatsRepository = seatsRepository;
    }


    @Override
    public BookingDto bookSeats(BookingForm bookingForm) {
        List<Seat> seats = new ArrayList<>();
        for (Integer i : bookingForm.getSeatNumbers()) {
            Optional<Seat> optionalSeat = seatsRepository.findByPlaceAndNumber(bookingForm.getPlaceId(), i);
            optionalSeat.ifPresent(seat -> {
                seat.setStatus(SeatStatus.BOOKED);
                seats.add(seat);
            });
        }

        Booking booking = builder()
                .startTime(bookingForm.getStartTime())
                .endTime(bookingForm.getEndTime())
                .user(fromUserDto(bookingForm.getUserDto()))
                .seats(seats)
                .build();

        bookingRepository.book(booking);

        return toBookingDto(booking);
    }

    @Override
    public void bookPlace(int placeId, long userId) {

    }
}
