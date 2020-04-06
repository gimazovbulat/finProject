package ru.itis.dao.impl;

import org.hibernate.exception.DataException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.RoomsRepository;
import ru.itis.dao.rowmappers.SeatRowMapper;
import ru.itis.models.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomsRepositoryImpl implements RoomsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SeatRowMapper seatRowMapper;
    @PersistenceContext
    EntityManager entityManager;

    public RoomsRepositoryImpl(JdbcTemplate jdbcTemplate,
                               SeatRowMapper seatRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.seatRowMapper = seatRowMapper;
    }

    @Override
    public Long save(Room room) {
        if (room.getId() <= 0) {
            entityManager.persist(room);
        } else {
            entityManager.merge(room);
        }
        return room.getId();
    }

    @Override
    public Optional<Room> find(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Room room) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteSeats(List<Room> rooms) {
        rooms.forEach(entityManager::remove);
    }

    @Override
    public Optional<Room> findByPlaceAndNumber(Integer placeId, Integer number) {
        String sql = "SELECT * FROM finproj.rooms WHERE place_id = ? AND number = ?";
        try {
            Room room = jdbcTemplate.queryForObject(sql, seatRowMapper, placeId, number);
            return Optional.of(room);
        } catch (DataException e) {
            throw new IllegalStateException(e);
        }
    }
}
