package org.example.model.repository;

import java.sql.SQLException;
import java.util.List;

public interface DefaultRepository<T> {
    T save(T entity) throws SQLException;
    T findById(Integer id);

    boolean remove(Integer id);

    List<T> findAll();
    T update(T entity);
}
