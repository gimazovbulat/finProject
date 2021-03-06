package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.PlaceDto;
import ru.itis.dto.RoomDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(schema = "finproj", name = "places")
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    @OneToOne
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    private FileInfo photo;
    @OrderBy("number")
    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
    private List<Room> rooms;

    public static Place fromPlaceDto(PlaceDto placeDto) {
        return Place.builder()
                .address(placeDto.getAddress())
                .id(placeDto.getId())
                .photo(FileInfo.fromFileInfoDto(placeDto.getPhoto()))
                .rooms(placeDto.getRooms().stream().map(Room::fromSeatDto).collect(Collectors.toList()))
                .build();

    }

    public static PlaceDto toPlaceDto(Place place) {
        List<RoomDto> freeSeats = place.getRooms().stream()
                .filter(room -> room.getStatus() == RoomStatus.FREE)
                .map(Room::tooRoomDto)
                .collect(Collectors.toList());

        return PlaceDto.builder()
                .id(place.getId())
                .photo(FileInfo.toFileDto(place.getPhoto()))
                .address(place.getAddress())
                .freeRooms(freeSeats)
                .rooms(place.getRooms().stream().map(Room::tooRoomDto).collect(Collectors.toList()))
                .build();
    }
}
