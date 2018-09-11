package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.dto.CreateUserRequest;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserService {
    User createNewUser(CreateUserRequest request) throws UserExistsException;
    User getUserById(Long id);
    boolean deleteUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto user);
    boolean userExists(String email);
    User getUserByEmail(String email) throws UserExistsException;
    User getUserByUsername(String username) throws UserNotFoundException;
}
