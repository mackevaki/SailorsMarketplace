package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.rest.AuthenticationDetails;
import com.company.sailorsmarketplace.rest.AuthenticationRequest;
import com.company.sailorsmarketplace.rest.CreateUserRequest;

import com.company.sailorsmarketplace.utils.AuthenticationUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UsersResourceTest {
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
    public void shouldCreateUserWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("/rest/accounts/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = randomAlphanumeric(5) + "@mail.ru";
        String username = randomAlphanumeric(8);
        CreateUserRequest createUserRequest = new CreateUserRequest(
                username,
                "pA1&ssword",
                "pA1&ssword",
                email,
                "+79150368714"
        );

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));
        Long createdUserId = Long.valueOf(response.readEntity(String.class));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));

        User createdUser = database.getByEmail(email);
        assertThat(createdUserId, equalTo(createdUser.getUserId()));
    }

    @Test
    public void shouldNotCreateUserWhenHeIsAlreadyRegistered() {
        WebTarget userWebTarget = target.path("/rest/accounts/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        User existedUser = database.getById(8L);
        CreateUserRequest createUserRequest = new CreateUserRequest(
                existedUser.getUsername(),
                existedUser.getPassword(),
                existedUser.getPassword(),
                existedUser.getEmail(),
                existedUser.getTelephone()
        );

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(400));
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsInvalid() {
        WebTarget userWebTarget = target.path("/rest/accounts/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String email = randomAlphanumeric(5) + "@mail.ru";
        String username = randomAlphanumeric(8);
        CreateUserRequest createUserRequest = new CreateUserRequest(
                username,
                "pA1&ssword",
                "pA1&sssword",
                email,
                "+79150368784"
        );

        Response response = invocationBuilder.post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));
        Long createdUserId = Long.valueOf(response.readEntity(String.class));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));

        User createdUser = database.getByEmail(email);
        assertThat(createdUserId, equalTo(createdUser.getUserId()));
    }



}
