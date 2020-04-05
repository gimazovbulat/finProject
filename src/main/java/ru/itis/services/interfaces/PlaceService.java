package ru.itis.services.interfaces;

import ru.itis.dto.PlaceDto;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    List<PlaceDto> getAll(int page, int size);

    Optional<PlaceDto> getById(int id);
}
