package org.example.model.repository;

import java.util.List;

public interface DefaultRepository<T> {
    void save(T entity);
    T findById(Integer id);

    boolean remove(Integer id);

    List<T> findAll();
    T update(T entity);
}
