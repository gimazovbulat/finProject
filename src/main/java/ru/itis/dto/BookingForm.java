package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingForm {
    private Integer[] roomNumbers;
    private String startDate;
    private String endDate;
    private Integer placeId;
    private UserDto userDto;
}
