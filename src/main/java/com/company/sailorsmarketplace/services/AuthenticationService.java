package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import com.google.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.sailorsmarketplace.dto.AllUserParams.Builder.allUserParamsDto;

public class AuthenticationService implements IAuthenticationService {
    @Inject
    private Database database;

    @Inject
    private IUserService userService;

    @Override
    public AllUserParams authenticate(String email, String userPassword) throws AuthenticationException, UserNotFoundException {
        AllUserParams userDto;

        User user = null;
        try {
            user = userService.getUserByEmail(email); // Email must be unique in our system
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(email);
        }

        // perform authentication business logic
        String secureUserPassword = null;
        secureUserPassword = AuthenticationUtil.generateSecurePassword(userPassword, user.getSalt());

        boolean authenticated = false;

        if (secureUserPassword != null && secureUserPassword.equalsIgnoreCase(user.getPassword())) {
            if (email != null && email.equalsIgnoreCase(user.getEmail())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthenticationException("Authentication failed");
        }

        userDto = allUserParamsDto()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .telephone(user.getTelephone())
                .salt(user.getSalt())
                .enabled(user.getEnabled())
                .build();

        return userDto;
    }

    @Override
    public String issueSecureToken(AllUserParams userDto) throws AuthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userDto.salt;
        String accessTokenMaterial = userDto.id + newSaltAsPostfix;
        byte[] encryptedAccessToken = null;

        try {
            encryptedAccessToken = AuthenticationUtil.encrypt(userDto.password, accessTokenMaterial);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        int tokenLength = encryptedAccessTokenBase64Encoded.length();
        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        storeAccessToken(userDto, tokenToSaveToDatabase);

        return returnValue;
    }

    @Override
    public AllUserParams resetSecurityCredentials(String password, AllUserParams userDto) {
        String salt = AuthenticationUtil.generateSalt(30);
        String secureUserPassword = AuthenticationUtil.generateSecurePassword(password, salt);

        userDto = allUserParamsDto()
                .id(userDto.id)
                .username(userDto.username)
                .email(userDto.email)
                .enabled(userDto.enabled)
                .telephone(userDto.telephone)
                .salt(salt)
                .password(secureUserPassword)
                .build();

        User user = new User(userDto.username, userDto.password, userDto.email, userDto.telephone);

        user.setUserId(userDto.id);
        user.setSalt(userDto.salt);
        user.setEnabled(userDto.enabled);

        database.update(user);

        return userDto;
    }

    @Override
    public User removeSecureCredentials(Long userId) {
        User user = database.getById(userId);

        user.setToken(null);
        user.setSalt(null);

        database.update(user);

        return user;
    }

    private void storeAccessToken(@NotNull AllUserParams userDto, String tokenToSaveToDatabase) {
        User user = database.getByEmail(userDto.email);

        user.setToken(tokenToSaveToDatabase);
        database.update(user);
    }


}
