package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public interface DAO<T> {
    T save(T entity);

    T getById(Long id);

    void update(T entity);

    void delete(T entity);
}
