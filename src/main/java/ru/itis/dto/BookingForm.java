package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingForm {
    private Integer[] seatNumbers;
    private Date startTime;
    private Date endTime;
    private Integer placeId;
    private UserDto userDto;
}
