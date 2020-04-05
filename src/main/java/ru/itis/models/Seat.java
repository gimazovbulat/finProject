package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.SeatDto;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "finproj", name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @ManyToMany(mappedBy = "seats")
    private List<Booking> booking;

    public Seat(Long id, Integer number, Place place, SeatStatus status) {
        this.id = id;
        this.number = number;
        this.place = place;
        this.status = status;
    }

    public static Seat fromSeatDto(SeatDto seatDto) {
        return Seat.builder()
                .id(seatDto.getId())
                .number(seatDto.getNumber())
                .place(seatDto.getPlace())
                .status(seatDto.getStatus())
                .build();
    }

    public static SeatDto toSeatDto(Seat seat) {
        return SeatDto.builder()
                .number(seat.getNumber())
                .status(seat.getStatus())
                .place(seat.getPlace())
                .id(seat.getId())
                .build();
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", number=" + number +
                ", status=" + status +
                '}';
    }
}
