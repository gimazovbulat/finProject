package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.PlaceRepository;
import ru.itis.models.Place;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PlaceRepositoryImpl implements PlaceRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<? extends Place> getAll(int size, int page) {
       return entityManager
                .createQuery("select p from Place p ")
                .setFirstResult(page - 1)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<Place> getByAddress(String address) {
        return Optional.empty();
    }

    @Override
    public Optional<Place> getById(Integer id) {
        return Optional.empty();
    }
}
