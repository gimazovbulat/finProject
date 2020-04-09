package ru.itis.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.RolesRepository;
import ru.itis.models.Role;

import java.util.Optional;

@Repository
public class RolesRepositoryImpl implements RolesRepository {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Role> rowMapper = ((rs, rowNum) -> {
        String roleName = rs.getString("name");
        Integer roleId = rs.getInt("id");
        return new Role(roleId, roleName);
    });

    public RolesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Role> findById(int id) {
        String sql = "SELECT * FROM finproj.roles WHERE id = ?";
        Role role = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.of(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        String sql = "SELECT * FROM finproj.roles WHERE name = ?";
        Role role = jdbcTemplate.queryForObject(sql, rowMapper, name);
        return Optional.of(role);
    }
}
