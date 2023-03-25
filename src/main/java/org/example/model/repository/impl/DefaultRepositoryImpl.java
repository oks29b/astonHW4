package org.example.model.repository.impl;

import org.example.model.repository.DefaultRepository;

import java.util.List;

public class DefaultRepositoryImpl<T> implements DefaultRepository<T> {
    @Override
    public void save(T entity) {
//        Session session = DataSource.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        session.saveOrUpdate(entity);
//        transaction.commit();
    }

    @Override
    public boolean remove(Integer id) {
return false;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public T findById(Integer id) {
        return null;
    }


}
