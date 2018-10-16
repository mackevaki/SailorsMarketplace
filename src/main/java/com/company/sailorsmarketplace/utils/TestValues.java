package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dao.UserProfileInfoDAO;
import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.services.IAuthenticationService;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.company.sailorsmarketplace.dto.AllUserParams.Builder.allUserParamsDto;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Singleton
public class TestValues {

    @Inject
    private IAuthenticationService authenticationService;

    @Inject
    private Database userDAO;


    public Map initialTableWithNTestUsers(int number) {
        Map<Long, User> testUsers = new HashMap<>();

        User admin = createAdministrator();
        testUsers.put(admin.getUserId(), admin);

        for (int i = 0; i < number; i++) {
            User tmp = createTestUser();
            testUsers.put(tmp.getUserId(), tmp);
        }

        return testUsers;
    }

    @NotNull
    public User createTestUser() {
        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String username = randomAlphanumeric(8);
        String password = "#testPass1";
        String telephone = "+" + randomNumeric(11);

        String salt = AuthenticationUtil.generateSalt(20);
        String securePassword = AuthenticationUtil.generateSecurePassword(password, salt);

        while (userDAO.getByEmail(email) != null) {
            email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        }

        User testUser = new User(
                username,
                securePassword,
                email,
                telephone,
                Authority.ROLE_USER
        );

        testUser.setSalt(salt);
        testUser.setEnabled(false);

        userDAO.save(testUser);
        User user = userDAO.getByEmail(testUser.getEmail());

        UserProfileInfo userProfileInfo = new UserProfileInfo(user.getUserId());
        userProfileInfo.setUser(user);

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        dao.save(userProfileInfo);

        user.setUserProfileInfo(userProfileInfo);
        userDAO.update(user);

        return user;
    }


    public User createTestUserByAuthority(Authority authority, String prefix) {
        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String username = prefix + randomAlphanumeric(8);
        String password = prefix + "#Pass1" + randomAlphanumeric(5);
        String telephone = "+" + randomNumeric(11);

        String salt = AuthenticationUtil.generateSalt(20);
        String securePassword = AuthenticationUtil.generateSecurePassword(password, salt);

        while (userDAO.getByEmail(email) != null) {
            email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        }

        User testUser = new User(
                username,
                securePassword,
                email,
                telephone,
                authority
        );

        testUser.setSalt(salt);
        testUser.setEnabled(false);
        userDAO.save(testUser);

        User user = userDAO.getByEmail(testUser.getEmail());
        UserProfileInfo userProfileInfo = new UserProfileInfo(user.getUserId());
        userProfileInfo.setUser(user);

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        dao.save(userProfileInfo);

        user.setUserProfileInfo(userProfileInfo);
        userDAO.update(user);

        return user;
    }


    @NotNull
    public User createAdministrator() {
        return createTestUserByAuthority(Authority.ROLE_ADMIN, "Admin_");
    }


    public boolean removeTestUser(@NotNull User testUser) {

        User entity = userDAO.getById(testUser.getUserId());
        userDAO.delete(entity);

        return userDAO.getById(testUser.getUserId()) == null;
    }

    @NotNull
    public AuthenticationRequest createTestUserForAutorization()  {

        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);

        String username = randomAlphanumeric(8);
        String password = "#testPass1" + randomAlphabetic(4);
        String telephone = "+" + randomNumeric(11);

        String salt = AuthenticationUtil.generateSalt(20);
        String securePassword = AuthenticationUtil.generateSecurePassword(password, salt);

        while (userDAO.getByEmail(email) != null) {
            email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        }

        User testUser = new User(
                username,
                securePassword,
                email,
                telephone,
                Authority.ROLE_USER
        );

        testUser.setSalt(salt);
        testUser.setEnabled(false);

        userDAO.save(testUser);
        User user = userDAO.getByEmail(testUser.getEmail());

        UserProfileInfo userProfileInfo = new UserProfileInfo(user.getUserId());
        userProfileInfo.setUser(user);

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        dao.save(userProfileInfo);

        user.setUserProfileInfo(userProfileInfo);
        userDAO.update(user);

        return new AuthenticationRequest(email, password);
    }

    public AuthenticationDetails createSignatedInUserWithCredentials()  {
        AuthenticationRequest loginDetails = createTestUserForAutorization();
        User createdUser = userDAO.getByEmail(loginDetails.email);

        AllUserParams userDto = allUserParamsDto().email(loginDetails.email)
                .username(createdUser.getUsername())
                .password(createdUser.getPassword())
                .id(createdUser.getUserId())
                .telephone(createdUser.getTelephone())
                .enabled(createdUser.getEnabled())
                .salt(createdUser.getSalt())
                .authorities(createdUser.getAuthorities()).build();

        userDto = authenticationService.resetSecurityCredentials(loginDetails.password, userDto);

        String secureUserToken = authenticationService.issueSecureToken(userDto);

        return new AuthenticationDetails(userDto.id, secureUserToken);
    }
}
