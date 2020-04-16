package ru.itis.dao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.dao.interfaces.PlacesRepository;
import ru.itis.models.Place;
import ru.itis.models.Room;
import ru.itis.models.RoomStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class RoomRowMapper implements RowMapper<Room> {
    private final PlacesRepository placesRepository;

    public RoomRowMapper(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        Integer placeId = rs.getInt("place_id");
        String seatStatus = rs.getString("status");
        Integer number = rs.getInt("number");
        Integer price = rs.getInt("price");

        Optional<Place> optionalPlace = placesRepository.findById(placeId);
        if (optionalPlace.isPresent()) {
            return Room.builder()
                    .id(id)
                    .price(price)
                    .number(number)
                    .place(optionalPlace.get())
                    .status(RoomStatus.valueOf(seatStatus))
                    .build();
        }
        throw new IllegalStateException(); //todo exception про отсут строку надо бы
    }
}
