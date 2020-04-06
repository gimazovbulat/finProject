package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Seat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private Long userId;
    private Date startTime;
    private Date endTime;
    private List<Seat> seats;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", user=" + userId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", seats=" + seats +
                '}';
    }
}
