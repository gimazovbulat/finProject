package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.itis.dto.BookingDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JoinTable(schema = "finproj", name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Seat> seats;

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .seats(booking.getSeats())
                .id(booking.getId())
                .userDto(User.toUserDto(booking.getUser()))
                .build();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", seats=" + seats +
                ", user=" + user.getEmail() +
                '}';
    }
}
