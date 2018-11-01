package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.utils.TestValues;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class AuthenticationTest {
    private WebTarget target;

    private final UserRepository userRepo;
    private final TestValues testValues;

    @Inject
    public AuthenticationTest(final UserRepository userRepo, final TestValues testValues) {
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
    public void shouldLoginWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        AuthenticationRequest loginRequest = testValues.createTestUserForAuthorization();

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        AuthenticationDetails authenticationDetails = response.readEntity(AuthenticationDetails.class);

        System.out.println(authenticationDetails.id + " - id; token: " + authenticationDetails.token);

        userRepo.getById(authenticationDetails.id).ifPresent(testValues::removeTestUser);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotLoginWhenUserWithThisEmailNotExists() {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        String password = "#NotExists1";

        while (userRepo.getByEmail(email) != null) {
            email = randomAlphanumeric(7, 20) + "@" + randomAlphabetic(2, 14)+ "." + randomAlphabetic(2, 5);
        }

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void shouldNotLoginWhenUserWithThisEmailExistsButPasswordNotMatches()  {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        AuthenticationRequest rightLoginInfo = testValues.createTestUserForAuthorization();
        AuthenticationRequest loginRequestWithWrongPassword = new AuthenticationRequest(
                rightLoginInfo.email,
                rightLoginInfo.password + "Wrong"
        );

        Response response = invocationBuilder.post(Entity.entity(loginRequestWithWrongPassword, MediaType.APPLICATION_JSON));

        userRepo.getByEmail(rightLoginInfo.email).ifPresent(testValues::removeTestUser);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

}
