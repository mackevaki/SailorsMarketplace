package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.rest.CreateUserRequest;

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
//        System.out.println("-----------  " + ((UserDto)response.getEntity()).getUsername());

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
        String userDto = response.readEntity(String.class);

//        assertNotNull(createdEntity.getUsername());
//        assertNotNull(createdEntity.getId());
    }


    private void setUp() {
    }
}
