package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.aspects.SendBookingEmail;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.RoomsRepository;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.BookingDto;
import ru.itis.dto.BookingForm;
import ru.itis.dto.RoomDto;
import ru.itis.models.Booking;
import ru.itis.models.Room;
import ru.itis.models.RoomStatus;
import ru.itis.models.User;
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
    private final UsersRepository usersRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomsRepository roomsRepository,
                              UsersRepository usersRepository) {
        this.bookingRepository = bookingRepository;
        this.roomsRepository = roomsRepository;
        this.usersRepository = usersRepository;
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

        Booking booking = Booking.builder()
                .user(fromUserDto(bookingForm.getUserDto()))
                .startDate(startDate)
                .endDate(endDate)
                .rooms(rooms)
                .paid(false)
                .cost(calculateCost(startDate, endDate, rooms.stream().map(Room::tooRoomDto).collect(Collectors.toList())))
                .build();

        bookingRepository.save(booking);

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

    @Override
    public BookingDto pay(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.find(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            Optional<User> optionalUser = usersRepository.findByEmail(booking.getUser().getEmail());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Integer userPoints = user.getPoints();
                if (userPoints < booking.getCost()) {
                    throw new IllegalStateException("You don't have enough points :(");
                } else {
                    user.setPoints(user.getPoints() - booking.getCost());
                    usersRepository.update(user);
                    booking.setPaid(true);
                }
                return Booking.toBookingDto(booking);
            }
        }
        throw new IllegalStateException();

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
