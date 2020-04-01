package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import ru.itis.dao.interfaces.PlaceRepository;
import ru.itis.dto.PlaceDto;
import ru.itis.models.Place;
import ru.itis.services.interfaces.PlaceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<PlaceDto> getAll(int page, int size) {
        List<? extends Place> places = placeRepository.getAll(page, size);
        return places.stream().map(place -> PlaceDto.builder()
                .id(place.getId())
                .address(place.getAddress())
                .build()).collect(Collectors.toList());
    }
}
