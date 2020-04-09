package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.BookingDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "finproj", name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;
    @Column(name = "start_time")
    private LocalDate startDate;
    @Column(name = "end_time")
    private LocalDate endDate;
    @JoinTable(schema = "finproj", name = "booking_rooms",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Room> rooms;
    private boolean paid;
    private Integer cost;

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .rooms(booking.getRooms().stream().map(Room::tooRoomDto).collect(Collectors.toList()))
                .id(booking.getId())
                .email(booking.getUser().getEmail())
                .cost(booking.getCost())
                .paid(booking.isPaid())
                .build();
    }

    public static Booking fromBookingDto(BookingDto bookingDto) {
        return Booking.builder()
                .endDate(bookingDto.getEndDate())
                .startDate(bookingDto.getStartDate())
                .id(bookingDto.getId())
                .user(new User(bookingDto.getEmail()))
                .rooms(bookingDto.getRooms().stream().map(Room::fromSeatDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rooms=" + rooms +
                ", user=" + user.getEmail() +
                ", paid=" + paid +
                '}';
    }
}
