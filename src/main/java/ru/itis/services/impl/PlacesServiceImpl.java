package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.PlacesRepository;
import ru.itis.dto.PlaceDto;
import ru.itis.models.FileInfo;
import ru.itis.models.Place;
import ru.itis.services.interfaces.PlacesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlacesServiceImpl implements PlacesService {
    private final PlacesRepository placesRepository;

    public PlacesServiceImpl(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    @Override
    public List<PlaceDto> getAll(int page, int size) {
        List<? extends Place> places = placesRepository.findAll(page, size);
        return places
                .stream()
                .map(place -> PlaceDto.builder()
                        .address(place.getAddress())
                        .id(place.getId())
                        .photo(FileInfo.toFileDto(place.getPhoto()))
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public PlaceDto getById(int id) {
        Optional<Place> optionalPlace = placesRepository.findById(id);
        if (optionalPlace.isPresent()) {
            return optionalPlace.map(Place::toPlaceDto).get();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Integer getCount() {
        return placesRepository.countPlaces();
    }
}
