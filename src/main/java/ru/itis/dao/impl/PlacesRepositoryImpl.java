package ru.itis.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.PlacesRepository;
import ru.itis.dao.rowmappers.PlaceRowMapper;
import ru.itis.models.Place;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PlacesRepositoryImpl implements PlacesRepository {
    @PersistenceContext
    EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final PlaceRowMapper placeRowMapper;

    public PlacesRepositoryImpl(JdbcTemplate jdbcTemplate, PlaceRowMapper placeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.placeRowMapper = placeRowMapper;
    }

    @Override
    public List<? extends Place> getAll(int page, int size) {
        String sql = "SELECT * FROM finproj.places LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, placeRowMapper, size, --page);
    }

    @Override
    public Optional<Place> getByAddress(String address) {
        return Optional.of((Place) entityManager
                .createQuery("select place from Place place where place.address=?1")
                .setParameter(1, address)
                .getSingleResult());
    }


    @Override
    public Optional<Place> getById(Integer id) {
        return Optional.of((Place) entityManager
                .createQuery("select place from Place place where place.id=?1")
                .setParameter(1, id)
                .getSingleResult());
    }

    @Override
    public Integer countPlaces() {
        String sql = "SELECT COUNT(*) FROM finproj.places";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
