package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.rest.CreateUserRequest;
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
    public UserDto createNewUser(CreateUserRequest request) {
        UserDto userDto = createUserRequestToDto(request);

        UsersEntity userEntity = userDtoToUsersEntity(userDto);
        try {
            return usersEntityToUserDto(database.save(userEntity));
        }  catch (ExceptionInInitializerError e){
            System.out.println(e.getException().getMessage());
            return null;
        }
    }

    @Override
    public UserDto updateUser(UserDto user) {
//        UserDto user = createUserRequestToDto(userRequest);
        UsersEntity old = database.getById(user.getUserId());

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
        UsersEntity usersEntity = database.getByEmail(email);
        return usersEntity != null;
    }

    @Override
    public boolean validateUser(CreateUserRequest createUserRequest) {


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
//        database.delete(userDtoToUsersEntity(user));
        return deleteUserById(user.getUserId());//database.getById(user.getUserId()) == null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        UsersEntity entity = database.getById(id);
        database.delete(entity);
        return database.getById(id) == null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UsersEntity> usersEntities = database.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UsersEntity u : usersEntities) {
            userDtos.add(usersEntityToUserDto(u));
        }
        return userDtos;
    }

    private UsersEntity userDtoToUsersEntity(UserDto userDto) {
        UsersEntity userEntity = new UsersEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setTelephone(userDto.getTelephone());
        userEntity.setEnabled(userDto.getEnabled());
        userEntity.setSalt(userDto.getSalt());

        return userEntity;
    }

    private UserDto usersEntityToUserDto(UsersEntity usersEntity) {
        UserDto returnValue = new UserDto();
        returnValue.setUserId(usersEntity.getUserId());
        returnValue.setUsername(usersEntity.getUsername());
        returnValue.setPassword(usersEntity.getPassword());
        returnValue.setEmail(usersEntity.getEmail());
        returnValue.setTelephone(usersEntity.getTelephone());
        returnValue.setEnabled(usersEntity.getEnabled());
        returnValue.setSalt(usersEntity.getSalt());

        return returnValue;
    }

    private UserDto createUserRequestToDto(CreateUserRequest request) {
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
