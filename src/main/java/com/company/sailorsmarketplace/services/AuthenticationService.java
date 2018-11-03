package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.AuthenticationUtil;

import javax.inject.Inject;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.company.sailorsmarketplace.dto.AllUserParams.Builder.allUserParamsDto;
import static com.company.sailorsmarketplace.utils.AuthenticationUtil.verifyUserPassword;

public class AuthenticationService {

    private final UserRepository userRepo;
    private final UserService userService;

    @Inject
    public AuthenticationService(UserRepository userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    public AllUserParams authenticate(final String email, final String providedPassword)
            throws AuthenticationException, UserNotFoundException {
        final User user = userService.getUserByEmail(email);

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

    public AllUserParams resetSecurityCredentials(String password, AllUserParams userDto) {
        String salt = AuthenticationUtil.generateSalt(30);
        String secureUserPassword = AuthenticationUtil.passwordHash(password, salt);

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

        userRepo.update(user);

        return userDto;
    }

    public User removeSecureCredentials(final Long userId) {
        return userRepo.getById(userId)
                .map(user -> user.setToken(null))
                .map(user -> user.setSalt(null))
                .map(userRepo::update)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void storeAccessToken(final AllUserParams userDto, final String tokenToSaveToDatabase) {
        userRepo.getByEmail(userDto.email)
                .map(user -> user.setToken(tokenToSaveToDatabase))
                .map(userRepo::update)
                .orElseThrow(() -> new UserNotFoundException(userDto.email));
    }


}
