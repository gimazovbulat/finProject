package ru.itis.dao.interfaces;

import ru.itis.models.Place;

import java.util.List;
import java.util.Optional;

public interface PlacesRepository {
    List<? extends Place> findAll(int page, int size);

    Optional<Place> findByAddress(String address);

    Optional<Place> findById(Integer id);

    Integer countPlaces();
}
