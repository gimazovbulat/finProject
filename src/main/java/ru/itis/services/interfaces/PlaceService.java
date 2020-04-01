package ru.itis.services.interfaces;

import ru.itis.dto.PlaceDto;

import java.util.List;

public interface PlaceService {
    List<PlaceDto> getAll(int page, int size);
}
