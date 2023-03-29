package org.example.model.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DefaultRepository<T> {
    T save(T entity);
    Optional<T> findById(Integer id);
    boolean remove(Integer id);
    List<T> findAll();
    T update(T entity);
}
