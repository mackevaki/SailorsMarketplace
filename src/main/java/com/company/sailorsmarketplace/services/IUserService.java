package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.rest.CreateUserRequest;

import java.util.List;

public interface IUserService {
    UserDto createNewUser(CreateUserRequest request);
    UserDto getUserById(Long id);
    boolean deleteUser(UserDto user);
    boolean deleteUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto user);
    boolean userExists(String email);
    boolean validateUser(CreateUserRequest createUserRequest);
    UserDto getUserByEmail(String email) throws UserExistsException;
//    UserDto getUserByUsername(String username);
}
