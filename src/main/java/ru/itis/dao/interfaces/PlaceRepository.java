package ru.itis.dao.interfaces;

import ru.itis.models.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    List<? extends Place> getAll(int page, int size);

    Optional<Place> getByAddress(String address);

    Optional<Place> getById(Integer id);

}
