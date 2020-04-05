package ru.itis.dao.impl;

import org.hibernate.exception.DataException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.FilesRepository;
import ru.itis.models.FileInfo;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class FilesRepositoryImpl implements FilesRepository {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<FileInfo> rowMapper = (row, num) ->
            FileInfo.builder()
                    .url(row.getString("url"))
                    .type(row.getString("type"))
                    .storageFileName(row.getString("storage_name"))
                    .originalFileName(row.getString("original_name"))
                    .id(row.getLong("id"))
                    .build();


    public FilesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(FileInfo fileInfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlToSave = "INSERT INTO finproj.files (original_name, storage_name, type, url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlToSave, new String[]{"id"});
            ps.setString(1, fileInfo.getOriginalFileName());
            ps.setString(2, fileInfo.getStorageFileName());
            ps.setString(3, fileInfo.getType());
            ps.setString(4, fileInfo.getUrl());

            return ps;
        }, keyHolder);
        fileInfo.setId((Long) keyHolder.getKey());
        return fileInfo.getId();
    }

    @Override
    public Optional<FileInfo> find(Long id) {
        String sql = "select * from finproj.files where id = ?";
        FileInfo fileInfo;
        try {
            fileInfo = jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataException e) {
            throw new IllegalStateException(e);
        }
        return Optional.of(fileInfo);
    }

    @Override
    public void update(FileInfo fileInfo) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public FileInfo findByName(String storageName) {
        String sqlToFind = "SELECT * FROM finproj.files WHERE storage_name = ?";
        FileInfo fileInfo = jdbcTemplate.queryForObject(sqlToFind, rowMapper, storageName);
        return fileInfo;
    }
}
