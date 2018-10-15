package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dao.UserProfileInfoDAO;
import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.services.AuthenticationService;
import com.company.sailorsmarketplace.services.IAuthenticationService;
import com.company.sailorsmarketplace.services.IUserService;
import com.company.sailorsmarketplace.services.UserService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

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
//    public final static User anonymousUser = new User(
//            "anonymousGuest",
//            "#guestPass1",
//            "guest@guest.com",
//            "+79876543210",
//            Authority.ROLE_ANONYMOUS
//    );


//    public static void setValues() {
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction tx = session.beginTransaction();
//            Query query = session.createQuery("DELETE FROM User WHERE id IS NOT NULL");
//            session.save(administrator);
//            session.save(testUser);
//            tx.commit();
//        }
//    }


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

    @NotNull
    public User createAdministrator() {

        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String username = "admin_" + randomAlphanumeric(4);
        String telephone = "+" + randomNumeric(11);
        String password = "#Admin1337";

        return new User(
                username,
                password,
                email,
                telephone,
                Authority.ROLE_ADMIN
        );

//        return userDAO.save(administrator);
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

//        AuthenticationService authenticationService = new AuthenticationService();
        userDto = authenticationService.resetSecurityCredentials(loginDetails.password, userDto);

        String secureUserToken = authenticationService.issueSecureToken(userDto);

        return new AuthenticationDetails(userDto.id, secureUserToken);
    }
}
