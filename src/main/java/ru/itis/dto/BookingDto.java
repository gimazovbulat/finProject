package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<RoomDto> rooms;
    private boolean paid;
    private Integer cost;

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", userEmail=" + email +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rooms=" + rooms +
                ", paid=" + paid +
                '}';
    }
}
