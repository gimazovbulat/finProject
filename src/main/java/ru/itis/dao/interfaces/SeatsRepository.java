package ru.itis.dao.interfaces;

import ru.itis.models.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatsRepository extends CrudRepository<Long, Seat>{
    Optional<Seat> findByPlaceAndNumber(Integer placeId, Integer number);

    void deleteSeats(List<Seat> seats);
}
