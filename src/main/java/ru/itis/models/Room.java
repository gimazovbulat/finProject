package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.RoomDto;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "finproj", name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    @ManyToMany(mappedBy = "rooms")
    private List<Booking> booki5ng;
    @Column(name = "price")
    private Integer price;

    public Room(Long id, Integer number, Place place, RoomStatus status) {
        this.id = id;
        this.number = number;
        this.place = place;
        this.status = status;
    }

    public static Room fromSeatDto(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .number(roomDto.getNumber())
                .place(roomDto.getPlace())
                .price(roomDto.getPrice())
                .status(roomDto.getStatus())
                .build();
    }

    public static RoomDto tooRoomDto(Room room) {
        return RoomDto.builder()
                .number(room.getNumber())
                .status(room.getStatus())
                .place(room.getPlace())
                .price(room.getPrice())
                .id(room.getId())
                .build();
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
