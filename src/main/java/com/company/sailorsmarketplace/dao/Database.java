package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

import java.util.List;

public interface Database {
    User save(User user);
    User getById(Long id);
    User getByUsername(String username) throws UserNotFoundException;
    User getByEmail(String email) throws UserNotFoundException;
    void update(User user);
    void delete(User user);
    List<User> findAll();
}
