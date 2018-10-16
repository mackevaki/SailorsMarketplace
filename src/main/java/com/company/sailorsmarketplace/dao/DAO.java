package com.company.sailorsmarketplace.dao;

public interface DAO<T> {
    T save(T entity);

    T getById(Long id);

    void update(T entity);

    void delete(T entity);
}
