package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;

import java.util.List;

public interface IUserService {
    User createNewUser(CreateUpdateUserParams params);

    User getUserById(Long id);

    boolean deleteUser(Long id);

    List<User> getAllUsers();

    User updateUser(CreateUpdateUserParams params, Long userId);

    boolean userExists(String email);

    User getUserByEmail(String email) throws UserExistsException;

    User getUserByUsername(String username);
}
