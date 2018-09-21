package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParamsDto;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.validation.constraints.NotNull;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.sailorsmarketplace.dto.AllUserParamsDto.Builder.allUserParamsDto;

@Singleton
public class AuthenticationService implements IAuthenticationService {
    @Inject
    Database database;

    @Inject
    IUserService userService;

//    @Inject
//    public AuthenticationService(Database database) {
//        this.database = database;
////        this.authenticationUtil = authenticationUtil;
//    }


    @Override
    public AllUserParamsDto authenticate(String email, String userPassword) throws AuthenticationException, UserNotFoundException {
        AllUserParamsDto userDto;

        User user = null;
        try {
            user = userService.getUserByEmail(email); // Email must be unique in our system
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(email);
        }
        // Here we perform authentication business logic
        // If authentication fails, we throw new AuthenticationException
        // other wise we return User Profile Details
        String secureUserPassword = null;
        secureUserPassword = AuthenticationUtil.
                generateSecurePassword(userPassword, user.getSalt());
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
    public String issueSecureToken(AllUserParamsDto userDto) throws AuthenticationException {
        String returnValue = null;
        // Get salt but only part of it
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
        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();
        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
        //userDto.setToken(tokenToSaveToDatabase);
        storeAccessToken(userDto, tokenToSaveToDatabase);
        return returnValue;
    }

    @Override
    public AllUserParamsDto resetSecurityCredentials(String password, AllUserParamsDto userDto) {
        // Generate salt
        String salt = AuthenticationUtil.generateSalt(30);
        // Generate secure user password
        String secureUserPassword = null;
        secureUserPassword = AuthenticationUtil.
                generateSecurePassword(password, salt);
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

        this.database.update(user);

        return userDto;
    }

    private void storeAccessToken(@NotNull AllUserParamsDto userDto, String tokenTosaveToDatabase) {
        User user = new User(userDto.username, userDto.password, userDto.email, userDto.telephone);
        user.setUserId(userDto.id);
        user.setSalt(userDto.salt);
        user.setEnabled(userDto.enabled);
        // Store to database
        this.database.update(user);
    }
}
