package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDto {
    private int id;
    private String address;
    private FileInfoDto photo;
    private List<SeatDto> seats;
    private List<SeatDto> freeSeats;
}
