package com.sammidev.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PersonRepository<T, ID> {
    T save(T value) throws SQLException;
    T update(T value) throws SQLException;
    Boolean removeById(ID value) throws SQLException;
    Boolean removeByEmail(ID value) throws SQLException;
    public Optional<T> findById(ID value) throws SQLException;
    public Optional<T> findByEmail(ID value) throws SQLException;
    public List<T> findAll() throws SQLException;
    public List<T> findAll(Long start, Long limit, Long orderIndex, String orderDirection, T param) throws SQLException;
}