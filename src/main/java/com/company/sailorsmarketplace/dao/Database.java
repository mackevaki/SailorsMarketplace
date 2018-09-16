package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.User;

import java.util.List;

public interface Database {
    User save(User user);

    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    void update(User user);

    void delete(User user);

    List<User> findAll();
}
