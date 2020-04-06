package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.aspects.SendMailAnno;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.SeatsRepository;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.models.Booking;
import ru.itis.models.Seat;
import ru.itis.models.SeatStatus;
import ru.itis.services.interfaces.BookingService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @SendMailAnno
    @SneakyThrows
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Booking booking = Booking.builder()
                .user(fromUserDto(bookingForm.getUserDto()))
                .startTime(simpleDateFormat.parse(bookingForm.getStartTime()))
                .endTime(simpleDateFormat.parse(bookingForm.getEndTime()))
                .seats(seats)
                .build();

        bookingRepository.book(booking);

        booking.getUser().getBookings().add(booking);

        return Booking.toBookingDto(booking);
    }

    @Override
    public void bookPlace(int placeId, long userId) {

    }
}
