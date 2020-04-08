package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Place;
import ru.itis.models.RoomStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Long id;
    private int number;
    private Place place;
    private RoomStatus status;
    private Integer price;
}
