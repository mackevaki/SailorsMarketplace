package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.rest.CreateUpdateUserRequest;
import com.company.sailorsmarketplace.utils.PasswordUtils;
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
    public UserDto updateUser(UserDto user) {
//        UserDto user = createUserRequestToDto(userRequest);
        User old = database.getById(user.getUserId());

        if (!old.getEmail().equals(user.getEmail())) {
            old.setEmail(user.getEmail());
        }
        if (!old.getUsername().equals(user.getUsername())) {
            old.setUsername(user.getUsername());
        }
        if (!old.getPassword().equals(user.getPassword())) {
            old.setPassword(user.getPassword());
        }
        if (!old.getTelephone().equals(user.getTelephone())) {
            old.setTelephone(user.getTelephone());
        }
        if (!old.getEnabled().equals(user.getEnabled())) {
            old.setEnabled(user.getEnabled());
        }

        if ((old.getUsername() != null) && (user.getUserId() != 0)) {
            database.update(old);
            return usersEntityToUserDto(database.getById(user.getUserId()));
        }
        return null;
    }

    @Override
    public boolean userExists(String email) {
        User user = database.getByEmail(email);
        return user != null;
    }

    @Override
    public boolean validateUser(CreateUpdateUserRequest createUpdateUserRequest) {


        return false;
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserExistsException {
        UserDto userDto = usersEntityToUserDto(database.getByEmail(email));
        if (userDto == null) {
            throw new UserExistsException(email);
        }
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserDto userDto = usersEntityToUserDto(database.getById(id));
        if (userDto == null) {
            return null;
        }
        return userDto;
    }

    @Override
    public boolean deleteUser(UserDto user) {
//        database.delete(paramsToUsersEntity(user));
        return deleteUser(user.getUserId());//database.getById(user.getUserId()) == null;
    }

    @Override
    public boolean deleteUser(Long id) {
        User entity = database.getById(id);
        database.delete(entity);
        return database.getById(id) == null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> usersEntities = database.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User u : usersEntities) {
            userDtos.add(usersEntityToUserDto(u));
        }
        return userDtos;
    }

    private UserDto usersEntityToUserDto(User user) {
        UserDto returnValue = new UserDto();
        returnValue.setUserId(user.getUserId());
        returnValue.setUsername(user.getUsername());
        returnValue.setPassword(user.getPassword());
        returnValue.setEmail(user.getEmail());
        returnValue.setTelephone(user.getTelephone());
        returnValue.setEnabled(user.getEnabled());
        returnValue.setSalt(user.getSalt());

        return returnValue;
    }

    private UserDto createUserRequestToDto(CreateUpdateUserRequest request) {
        UserDto userDto = new UserDto();
        userDto.setUsername(request.username);
        userDto.setEmail(request.email);
        userDto.setTelephone(request.telephone);
        userDto.setEnabled((byte) 1);
        String salt = PasswordUtils.getSalt(10);
        userDto.setSalt(salt);
        userDto.setPassword(PasswordUtils.generateSecurePassword(userDto.getPassword(), salt));

        return userDto;
    }

}
