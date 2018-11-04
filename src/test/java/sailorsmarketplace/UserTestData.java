package sailorsmarketplace;

import com.company.sailorsmarketplace.dao.UserProfileInfoRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.services.AuthenticationService;

import com.company.sailorsmarketplace.utils.AuthenticationUtil;
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
public class UserTestData {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepo;

    @Inject
    public UserTestData(UserRepository userRepo, AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
    }


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
        return createTestUserByAuthority(Authority.ROLE_USER, "User_");
    }

    public User createTestUserByAuthority(Authority authority, String prefix) {
        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String password = prefix + "#Pass1" + randomAlphanumeric(5);
        return createTestUserByAuthority(authority, prefix, email, password);
    }

    public User createTestUserByAuthority(Authority authority, String prefix, String email, String password) {
//        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String username = prefix + randomAlphanumeric(8);
//        String password = prefix + "#Pass1" + randomAlphanumeric(5);
        String telephone = "+" + randomNumeric(11);
        String salt = AuthenticationUtil.generateSalt(20);
        String securePassword = AuthenticationUtil.generateSecurePassword(password, salt);
        User testUser = new User(
                username,
                securePassword,
                email,
                telephone,
                authority
        );

        testUser.setSalt(salt);
        testUser.setEnabled(false);
        userRepo.save(testUser);

//        User user = userRepo.getByEmail(testUser.getEmail()).orElseThrow(() -> new UserNotFoundException(testUser.getEmail()));
        UserProfileInfo userProfileInfo = new UserProfileInfo(testUser.getUserId());
        userProfileInfo.setUser(testUser);

        UserProfileInfoRepository dao = new UserProfileInfoRepository();
        dao.save(userProfileInfo);

        testUser.setUserProfileInfo(userProfileInfo);
        return userRepo.update(testUser);
    }


    @NotNull
    public User createAdministrator() {
        return createTestUserByAuthority(Authority.ROLE_ADMIN, "Admin_");
    }


    public void removeTestUser(final User testUser) {
        userRepo.delete(testUser);
    }

    @NotNull
    public AuthenticationRequest createTestUserForAutorization()  {
        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String password = "#testPass1" + randomAlphabetic(4);

        createTestUserByAuthority(Authority.ROLE_USER, "User_", email, password);

        return new AuthenticationRequest(email, password);
    }

    public AuthenticationDetails createSignatedInUserWithCredentials()  {
        AuthenticationRequest loginDetails = createTestUserForAutorization();
        User createdUser = userRepo.getByEmail(loginDetails.email)
                .orElseThrow(() -> new IllegalStateException("No created user with generated email"));

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
