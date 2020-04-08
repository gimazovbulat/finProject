package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.dao.interfaces.PaymentRepository;
import ru.itis.dao.interfaces.UsersRepository;
import ru.itis.dto.PaymentDto;
import ru.itis.models.Booking;
import ru.itis.models.Payment;
import ru.itis.models.PaymentStatus;
import ru.itis.models.User;
import ru.itis.services.interfaces.PaymentService;

import java.util.Optional;

@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final UsersRepository usersRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UsersRepository usersRepository,
                              BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.usersRepository = usersRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public PaymentDto pay(PaymentDto paymentDto) {
        Payment payment;
        Optional<Booking> optionalBooking = bookingRepository.find(paymentDto.getBookingId());
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            Optional<User> optionalUser = usersRepository.findByEmail(booking.getUser().getEmail());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Integer userPoints = user.getPoints();
                if (userPoints < paymentDto.getCost()) {
                    throw new IllegalStateException("You don't have enough points :(");
                } else {
                    payment = Payment.fromPaymentDto(paymentDto);
                    payment.setBooking(booking);
                    user.setPoints(user.getPoints() - payment.getCost());
                    System.out.println(user);
                    usersRepository.update(user);
                    payment.setPaymentStatus(PaymentStatus.PAYED);
                    paymentRepository.save(payment);
                }
                return Payment.toPaymentDto(payment);
            }
        }
        throw new IllegalStateException();
    }

}
