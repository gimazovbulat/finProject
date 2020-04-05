package ru.itis.dao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.dao.interfaces.PlacesRepository;
import ru.itis.models.Place;
import ru.itis.models.Seat;
import ru.itis.models.SeatStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class SeatRowMapper implements RowMapper<Seat> {
    private final PlacesRepository placesRepository;

    public SeatRowMapper(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    @Override
    public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        Integer placeId = rs.getInt("place_id");
        String seatStatus = rs.getString("status");
        Integer number = rs.getInt("number");

        Optional<Place> optionalPlace = placesRepository.getById(placeId);
        if (optionalPlace.isPresent()) {
            return new Seat(id, number, optionalPlace.get(), SeatStatus.valueOf(seatStatus));
        }
        throw new IllegalStateException(); //todo exception про отсут строку надо бы
    }
}
