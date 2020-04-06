package ru.itis.services.interfaces;

import ru.itis.dto.PlaceDto;

import java.util.List;
import java.util.Optional;

public interface PlacesService {
    List<PlaceDto> getAll(int page, int size);

    PlaceDto getById(int id);

    Integer getCount();
}
