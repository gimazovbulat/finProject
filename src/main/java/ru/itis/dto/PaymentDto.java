package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private Integer cost;
    private PaymentStatus paymentStatus;
    private Long bookingId;

    @Override
    public String toString() {
        return "PaymentDto{" +
                "id=" + id +
                ", cost=" + cost +
                ", booking=" + bookingId +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
