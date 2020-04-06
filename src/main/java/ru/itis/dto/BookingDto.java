package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Seat;
import ru.itis.models.User;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private String email;
    private Date startTime;
    private Date endTime;
    private List<SeatDto> seats;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", userEmail=" + email +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", seats=" + seats +
                '}';
    }
}
