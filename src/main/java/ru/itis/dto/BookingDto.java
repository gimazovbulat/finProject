package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<RoomDto> rooms;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", userEmail=" + email +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rooms=" + rooms +
                '}';
    }
}
