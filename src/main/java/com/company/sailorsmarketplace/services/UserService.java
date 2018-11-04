package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.CreateUpdateUserParams;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class UserService {
    private final UserRepository userRepo;

    @Inject
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createNewUser(final CreateUpdateUserParams params, final Authority authority) throws UserExistsException {
        if (userExists(params.email)) {
            throw new UserExistsException(params.email);
        }

        String salt = AuthenticationUtil.generateSalt(20);
        String securePassword = AuthenticationUtil.generateSecurePassword(params.password, salt);

        final User userEntity = new User(
                params.username,
                securePassword,
                params.email,
                params.telephone,
                authority
        );

        userEntity.setSalt(salt);
            userEntity.setEnabled(false); // will be true after activation

        return userRepo.save(userEntity);
    }

    public User updateUser(final CreateUpdateUserParams updateParams, final Long userId) {
        return userRepo.getById(userId)
                .map(user -> user.setEmail(updateParams.email))
                .map(user -> user.setPassword(updateParams.password))
                .map(user -> user.setUsername(updateParams.username))
                .map(user -> user.setTelephone(updateParams.telephone))
                .map(user -> {
                    userRepo.update(user);
                    return user;
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public boolean userExists(final String email) {
        return userRepo.getByEmail(email).isPresent();
    }

    public User getUserByEmail(final String email) throws UserNotFoundException {
        return userRepo.getByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User getUserByUsername(final String username) {
        return userRepo.getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(format("Can't find user with username \"%s\"", username)));
    }

    public User getUserById(final Long id) throws UserNotFoundException {
        return userRepo.getById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(Long id) {
        userRepo.getById(id).ifPresent(userRepo::delete);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepo.findAll();
        return new ArrayList<>(users);
    }

}
