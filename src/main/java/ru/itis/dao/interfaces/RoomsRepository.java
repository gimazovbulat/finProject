package ru.itis.dao.interfaces;

import ru.itis.models.Room;

import java.util.List;
import java.util.Optional;

public interface RoomsRepository extends CrudRepository<Long, Room>{
    Optional<Room> findByPlaceAndNumber(Integer placeId, Integer number);

    void deleteSeats(List<Room> rooms);
}
