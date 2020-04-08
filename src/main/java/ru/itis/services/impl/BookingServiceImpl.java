package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.aspects.SendBookingEmail;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.PaymentRepository;
import ru.itis.dao.interfaces.RoomsRepository;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.dto.RoomDto;
import ru.itis.models.*;
import ru.itis.services.interfaces.BookingService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.itis.models.User.fromUserDto;


@Transactional
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomsRepository roomsRepository;
    private final PaymentRepository paymentRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomsRepository roomsRepository,
                              PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.roomsRepository = roomsRepository;
        this.paymentRepository = paymentRepository;
    }

    @SendBookingEmail
    @SneakyThrows
    @Override
    public BookingDto bookRooms(BookingForm bookingForm) {
        List<Room> rooms = new ArrayList<>();
        for (Integer i : bookingForm.getRoomNumbers()) {
            Optional<Room> optionalRoom = roomsRepository.findByPlaceAndNumber(bookingForm.getPlaceId(), i);
            optionalRoom.ifPresent(room -> {
                room.setStatus(RoomStatus.BOOKED);
                rooms.add(room);
            });
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(bookingForm.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(bookingForm.getEndDate(), formatter);

        Payment payment = Payment.builder()
                .cost(calculateCost(startDate, endDate, rooms.stream().map(Room::tooRoomDto).collect(Collectors.toList())))
                .paymentStatus(PaymentStatus.NOT_PAYED)
                .build();

        Booking booking = Booking.builder()
                .user(fromUserDto(bookingForm.getUserDto()))
                .startDate(startDate)
                .endDate(endDate)
                .rooms(rooms)
                .payment(payment)
                .build();

        bookingRepository.save(booking);

        payment.setBooking(booking);
        System.out.println("booking " + booking);
        System.out.println("payment " + payment);
        paymentRepository.save(payment);

        booking.getUser().getBookings().add(booking);

        return Booking.toBookingDto(booking);
    }

    @Override
    public void bookPlace(int placeId, long userId) {

    }

    @Override
    public BookingDto findBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.find(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            return Booking.toBookingDto(booking);
        } else {
            throw new IllegalStateException("booking with that id doesn't exist");
        }
    }

    private Integer calculateCost(LocalDate startDate, LocalDate endDate, List<RoomDto> rooms) {
        Period period = Period.between(startDate, endDate);
        int days = period.getDays();

        int seatPriceSum = rooms.stream()
                .map(RoomDto::getPrice)
                .reduce(Integer::sum)
                .orElse(0);

        return seatPriceSum * days;
    }
}
