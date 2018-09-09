package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Database {
    UsersEntity save(UsersEntity user);
    UsersEntity getById(Long id);
    UsersEntity getByUsername(String username);
    UsersEntity getByEmail(String email);
    void update(UsersEntity user);
    void delete(UsersEntity user);
    List<UsersEntity> findAll();
}
