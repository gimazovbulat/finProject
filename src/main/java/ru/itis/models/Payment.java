package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.PaymentDto;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(schema = "finProj", name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;
    private Integer cost;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus paymentStatus;

    public static Payment fromPaymentDto(PaymentDto paymentDto) {
        return Payment.builder()
                .cost(paymentDto.getCost())
                .id(paymentDto.getId())
                .paymentStatus(paymentDto.getPaymentStatus())
                .build();
    }

    public static PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId())
                .cost(payment.getCost())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", booking=" + booking.getId() +
                ", cost=" + cost +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
