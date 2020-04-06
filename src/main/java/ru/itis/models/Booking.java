package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.BookingDto;

import javax.persistence.*;
import java.util.Date;
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
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @JoinTable(schema = "finproj", name = "booking_rooms",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Room> rooms;

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .rooms(booking.getRooms().stream().map(Room::toSeatDto).collect(Collectors.toList()))
                .id(booking.getId())
                .email(booking.getUser().getEmail())
                .build();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rooms=" + rooms +
                ", user=" + user.getEmail() +
                '}';
    }
}
