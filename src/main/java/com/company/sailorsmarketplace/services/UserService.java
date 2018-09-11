package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.dto.CreateUserRequest;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;


@Singleton
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

//    @Inject
//    private Database database;
    private UserDAO database = new UserDAO();

    @Override
    public User createNewUser(CreateUserRequest request) throws UserExistsException {
        UserDto userDto = createUserRequestToDto(request);
        log.info("dddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeee");
        User userEntity = userDtoToUsersEntity(userDto);
        if (userExists(userDto.getEmail())) {
            throw new UserExistsException(userDto.getEmail());
        }

        try {
            return database.save(userEntity);
        }  catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
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
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = database.getByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return database.getByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        User user = database.getById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public boolean deleteUserById(Long id) {
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

    private User userDtoToUsersEntity(UserDto userDto) {
        User userEntity = new User();
//        userEntity.setUserId(userDto.getUserId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setTelephone(userDto.getTelephone());
        userEntity.setEnabled(userDto.getEnabled());
        userEntity.setSalt(userDto.getSalt());

        return userEntity;
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

    private UserDto createUserRequestToDto(CreateUserRequest request) {
        UserDto userDto = new UserDto(
                request.username,
                request.password,
                request.email,
                request.telephone);
        userDto.setEnabled((byte) 1);
        String salt = AuthenticationUtil.generateSalt(10);
        userDto.setSalt(salt);
        userDto.setPassword(AuthenticationUtil.generateSecurePassword(request.password, salt));

        return userDto;
    }

}
