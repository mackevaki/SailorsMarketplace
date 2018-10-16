package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.HK2toGuiceModule;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.CreateUserRequest;

import com.company.sailorsmarketplace.utils.TestValues;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class UsersResourceTest {
    private WebTarget target;

    @Inject
    private Database database;

    @Inject
    private TestValues testValues;


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

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));
        Long createdUserId = Long.valueOf(response.readEntity(String.class));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));

        User createdUser = database.getByEmail(email);
        assertThat(createdUserId, equalTo(createdUser.getUserId()));
    }

    @Test
    public void shouldNotCreateUserWhenHeIsAlreadyRegistered() {
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

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        testValues.removeTestUser(existedUser);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsInvalid() {
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

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
    }


    @Test
    public void shouldShowUserProfileInfoWhenAllInputsAreValid() {
        User testUser = testValues.createTestUser();
        Long userId = testUser.getUserId();

        WebTarget userWebTarget = target.path("/rest/profile_info/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        String info = response.readEntity(String.class);
        System.out.println(info);

        testValues.removeTestUser(testUser);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
    }

    @Test
    public void shouldRemoveUserWhenThereAreAllCredentials() {
        AuthenticationDetails details = testValues.createSignatedInUserWithCredentials();
        String token = details.token;
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete();

        String info = response.readEntity(String.class);
        System.out.println(info);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
    }

    @Test
    public void shouldNotRemoveUserWhenTokenIsNotValid() {
        AuthenticationDetails details = testValues.createSignatedInUserWithCredentials();
        String invalidToken = details.token + "Invalid";
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder
                .header("Authorization", "Bearer " + invalidToken)
                .delete();

        String info = response.readEntity(String.class);
        System.out.println(info);
        testValues.removeTestUser(database.getById(details.id));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void shouldGetUserWhenThereAreAllCredentials() {
        AuthenticationDetails details = testValues.createSignatedInUserWithCredentials();
        String token = details.token;
        Long userId = details.id;

        WebTarget userWebTarget = target.path("/rest/users/" + userId);
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get();

        String info = response.readEntity(String.class);
        System.out.println(info);

        testValues.removeTestUser(database.getById(details.id));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
    }
}
