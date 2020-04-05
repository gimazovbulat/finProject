package ru.itis.dao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.dao.interfaces.FilesRepository;
import ru.itis.models.FileInfo;
import ru.itis.models.Place;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class PlaceRowMapper implements RowMapper<Place> {
    private final FilesRepository filesRepository;

    public PlaceRowMapper(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public Place mapRow(ResultSet rs, int rowNum) throws SQLException {
        Place place = new Place();
        do {
            String address = rs.getString("address");
            Integer id = rs.getInt("id");
            Optional<FileInfo> photo = filesRepository.find(rs.getLong("photo_id"));
            photo.ifPresent(place::setPhoto);
            place.setAddress(address);
            place.setId(id);
        }
        while (rs.next());
        return place;
    }
}
