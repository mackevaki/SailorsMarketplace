package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import com.google.inject.Inject;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.sailorsmarketplace.dto.AllUserParams.Builder.allUserParamsDto;
import static com.company.sailorsmarketplace.utils.AuthenticationUtil.verifyUserPassword;

public class AuthenticationService  {
    private final UserRepository userRepository;
    private final UserService userService;

    @Inject
    public AuthenticationService(final UserRepository userRepository, final UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public AllUserParams authenticate(final String email, final String providedPassword) {
        final User user =  userService.getUserByEmail(email); // Email must be unique in our system
        // perform authentication business logic
        final boolean authenticated = verifyUserPassword(providedPassword, user.getPassword(), user.getSalt());
        if (!authenticated) {
            throw new AuthenticationException("Authentication failed");
        }

        return allUserParamsDto()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .telephone(user.getTelephone())
                .salt(user.getSalt())
                .enabled(user.getEnabled())
                .build();
    }

    public String issueSecureToken(AllUserParams userDto) {
        final String newSaltAsPostfix = userDto.salt;
        final String accessTokenMaterial = userDto.id + newSaltAsPostfix;
        byte[] encryptedAccessToken;

        try {
            encryptedAccessToken = AuthenticationUtil.encrypt(userDto.password, accessTokenMaterial);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        final String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
        final int tokenLength = encryptedAccessTokenBase64Encoded.length();
        final String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);

        storeAccessToken(userDto, tokenToSaveToDatabase);
        return encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
    }

    public AllUserParams resetSecurityCredentials(final String password, AllUserParams userDto) {
        final String salt = AuthenticationUtil.generateSalt(30);
        final String secureUserPassword = AuthenticationUtil.hashPassword(password, salt);

        userDto = allUserParamsDto()
                .id(userDto.id)
                .username(userDto.username)
                .email(userDto.email)
                .enabled(userDto.enabled)
                .telephone(userDto.telephone)
                .salt(salt)
                .password(secureUserPassword)
                .build();

        final User user = new User(userDto.username, userDto.password, userDto.email, userDto.telephone);

        user.setUserId(userDto.id);
        user.setSalt(userDto.salt);
        user.setEnabled(userDto.enabled);

        userRepository.update(user);

        return userDto;
    }

    public User removeSecureCredentials(Long userId) {
       return userRepository.getById(userId)
               .map(user -> user.setToken(null))
               .map(user -> user.setSalt(null))
               .map(userRepository::update)
               .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void storeAccessToken(final AllUserParams userDto, final String tokenToSaveToDatabase) {
         userRepository.getByEmail(userDto.email)
                .map(user -> user.setToken(tokenToSaveToDatabase))
                 .map(userRepository::update)
                .orElseThrow(() -> new UserNotFoundException(userDto.email));
    }


}
