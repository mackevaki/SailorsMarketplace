package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;


@Singleton
public class UserService implements IUserService {
    @Inject
    private Database database;

    @Override
    public User createNewUser(CreateUpdateUserParams params) {
        final User userEntity = new User(
                params.username,
                params.password,
                params.email,
                params.telephone
        );

        return database.save(userEntity);
    }

    @Override
    public User updateUser(CreateUpdateUserParams user, Long userId) {
        User old = database.getByEmail(user.email);

        if (!old.getEmail().equals(user.email)) {
            old.setEmail(user.email);
        }
        if (!old.getUsername().equals(user.username)) {
            old.setUsername(user.username);
        }
        if (!old.getPassword().equals(user.password)) {
            old.setPassword(user.password);
        }
        if (!old.getTelephone().equals(user.telephone)) {
            old.setTelephone(user.telephone);
        }
//        if (!old.getEnabled().equals(user.getEnabled())) {
//            old.setEnabled(user.getEnabled());
//        }

//        if ((old.getUsername() != null) && (user.getUserId() != 0)) {
//            database.update(old);
//            return database.getById(user.getUserId());
//        }
        return null;
    }

    @Override
    public boolean userExists(String email) {
        User user = database.getByEmail(email);
        return user != null;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = database.getByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        User user = database.getById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }


    @Override
    public boolean deleteUser(Long id) {
        User entity = database.getById(id);
        database.delete(entity);
        return database.getById(id) == null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = database.findAll();
        return new ArrayList<>(users);
    }

}
