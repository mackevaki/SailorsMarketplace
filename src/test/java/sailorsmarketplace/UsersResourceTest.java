package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.CreateUserRequest;
import com.company.sailorsmarketplace.utils.TestValues;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class UsersResourceTest {
    private WebTarget target;

    private final UserRepository userRepo;
    private final TestValues testValues;

    @Inject
    public UsersResourceTest(UserRepository userRepo, TestValues testValues) {
        this.userRepo = userRepo;
        this.testValues = testValues;
    }

    @Before
    public void startServer() throws Exception {
        Launcher.startServer();

        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:" + Launcher.SERVER_PORT);
    }

    @After
    public void stopServer() throws Exception {
        Launcher.stopServer();
    }

    @Test
    public void shouldCreateUserWhenAllInputsAreValid() {
        // given
        WebTarget userWebTarget = target.path("/rest/users/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String username = randomAlphanumeric(8);
        String telephone = "+" + randomNumeric(11);

        CreateUserRequest createUserRequest = new CreateUserRequest(
                username,
                "Pass#word1",
                "Pass#word1",
                email,
                telephone
        );

        // when
        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotCreateUserWhenHeIsAlreadyRegistered() {
        // given
        WebTarget userWebTarget = target.path("/rest/users/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        User existedUser = testValues.createTestUser();

        CreateUserRequest createUserRequest = new CreateUserRequest(
                existedUser.getUsername(),
                existedUser.getPassword(),
                existedUser.getPassword(),
                existedUser.getEmail(),
                existedUser.getTelephone()
        );

        // when
        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));

        // cleanup
        testValues.removeTestUser(existedUser);
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsInvalid() {
        // given
        WebTarget userWebTarget = target.path("/rest/users/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = randomAlphanumeric(5);
        String username = randomAlphanumeric(8);
        String telephone = "+" + randomNumeric(11);

        CreateUserRequest createUserRequest = new CreateUserRequest(
                username,
                "pA1&ssword",
                "pA1&ssword",
                email,
                telephone
        );

        // when
        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }


    @Test
    public void shouldShowUserProfileInfoWhenAllInputsAreValid() {
        // given
        User testUser = testValues.createTestUser();
        Long userId = testUser.getUserId();

        WebTarget userWebTarget = target.path("/rest/profile_info/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder.get();

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));

        // cleanup
        testValues.removeTestUser(testUser);
    }

    @Test
    public void shouldRemoveUserWhenThereAreAllCredentials() {
        // given
        AuthenticationDetails details = testValues.createSignedInUserWithCredentials();
        String token = details.token;
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete();

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotRemoveUserWhenTokenIsNotValid() {
        // given
        AuthenticationDetails details = testValues.createSignedInUserWithCredentials();
        String invalidToken = details.token + "Invalid";
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder
                .header("Authorization", "Bearer " + invalidToken)
                .delete();

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));

        // cleanup
        userRepo.getById(details.id).ifPresent(testValues::removeTestUser);
    }

    @Test
    public void shouldGetUserWhenThereAreAllCredentials() {
        // given
        AuthenticationDetails details = testValues.createSignedInUserWithCredentials();
        String token = details.token;
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get();

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));

        // cleanup
        userRepo.getById(details.id).ifPresent(testValues::removeTestUser);
    }
}
