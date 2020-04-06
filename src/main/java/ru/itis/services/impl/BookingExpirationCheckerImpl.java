package ru.itis.services.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.services.interfaces.BookingExpirationChecker;

import java.util.Date;

@Component
public class BookingExpirationCheckerImpl implements BookingExpirationChecker {
    private final BookingRepository bookingRepository;

    public BookingExpirationCheckerImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Async
    @Scheduled(fixedDelay = 86400000L) //24 hours
    @Override
    public void check() {
        bookingRepository.removeOverdue(new Date());
    }
}
