package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.rest.AuthenticationDetails;
import com.company.sailorsmarketplace.rest.AuthenticationRequest;
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
        WebTarget userWebTarget = target.path("/rest/authentication");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        User existedUser = database.getById(8L);
        String email = existedUser.getEmail();
        String password = existedUser.getPassword();

        AuthenticationRequest loginRequest = new AuthenticationRequest(email, password);


        Response response = invocationBuilder.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON));
        AuthenticationDetails authenticationDetails = response.readEntity(AuthenticationDetails.class);
        System.out.println(authenticationDetails.getId() + " - id; tokrn: " + authenticationDetails.getToken());
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
    }

}
