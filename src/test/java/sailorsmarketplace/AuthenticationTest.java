package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class AuthenticationTest {
    private WebTarget target;
    private UserDAO database = new UserDAO();

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

        String email = "test@test.com";
        String password = "#TestUser1";

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        AuthenticationDetails authenticationDetails = response.readEntity(AuthenticationDetails.class);

        System.out.println(authenticationDetails.id + " - id; token: " + authenticationDetails.token);

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotLoginWhenUserWithThisEmailNotExists() {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = "notexists@not.exist";
        String password = "#NotExists1";

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void shouldNotLoginWhenUserWithThisEmailExistsButPasswordNotMatches() {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = database.getById(10L).getEmail();
        String password = "#IllegalPass1";

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void shouldNotLoginWhenEmailNotWellFormed() {
        WebTarget userWebTarget = target.path("/rest/authentication/login");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = "badEmailrf";
        String password = "#Password1";

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);

        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

}
