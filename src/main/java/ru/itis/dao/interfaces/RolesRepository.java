package ru.itis.dao.interfaces;

import ru.itis.models.Role;

import java.util.Optional;

public interface RolesRepository {
    Optional<Role> findById(int id);

    Optional<Role> findByName(String name);
}
