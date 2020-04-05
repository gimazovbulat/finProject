package ru.itis.dao.impl;

import org.hibernate.exception.DataException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.SeatsRepository;
import ru.itis.dao.rowmappers.SeatRowMapper;
import ru.itis.models.Seat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class SeatsRepositoryImpl implements SeatsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SeatRowMapper seatRowMapper;
    @PersistenceContext
    EntityManager entityManager;

    public SeatsRepositoryImpl(JdbcTemplate jdbcTemplate,
                               SeatRowMapper seatRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.seatRowMapper = seatRowMapper;
    }

    @Override
    public Long save(Seat seat) {
        if (seat.getId() <= 0) {
            entityManager.persist(seat);
        } else {
            entityManager.merge(seat);
        }
        return seat.getId();
    }

    @Override
    public Optional<Seat> find(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Seat seat) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Seat> findByPlaceAndNumber(Integer placeId, Integer number) {
        String sql = "SELECT * FROM finproj.seats WHERE place_id = ? AND number = ?";
        try {
            Seat seat = jdbcTemplate.queryForObject(sql, seatRowMapper, placeId, number);
            return Optional.of(seat);
        } catch (DataException e) {
            throw new IllegalStateException(e);
        }
    }
}
