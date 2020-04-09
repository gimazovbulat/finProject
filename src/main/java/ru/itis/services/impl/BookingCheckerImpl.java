package ru.itis.services.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.BookingRepository;
import ru.itis.services.interfaces.BookingChecker;

import java.time.LocalDate;

@Service
public class BookingCheckerImpl implements BookingChecker {
    private final BookingRepository bookingRepository;

    public BookingCheckerImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Async
    @Scheduled(fixedDelay = 86400000L) //24 hours
    @Override
    public void checkOverdue() {
        bookingRepository.removeOverdue(LocalDate.now());
    }

    @Transactional
    @Async
    @Scheduled(fixedDelay = 43200000L) //12 hours
    public void checkPaid() {
        bookingRepository.resetRooms(LocalDate.now());
    }

}
