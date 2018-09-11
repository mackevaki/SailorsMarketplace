package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dto.CreateUserRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UsersResourceTest {
    private WebTarget target;
    private CreateUserRequest createUserRequest;
    private static final Logger log = LoggerFactory.getLogger(Logger.class);

    @Before
    public void startServer() throws Exception {
        Launcher.startServer();

        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:" + Launcher.SERVER_PORT);
        setUp();
    }

    @After
    public void stopServer() throws Exception {
        Launcher.stopServer();
    }

    @Test
    public void createUserTest() {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        WebTarget userWebTarget = target.path("/rest").path("/accounts").path("/all");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);
        createUserRequest = new CreateUserRequest(
                "hfudiek",
                "hgj11ffsd",
                "hgj11ffsd",
                "kira@mail.ru",
                "89356757323"
        );
        Response response = invocationBuilder.get();//post(Entity.entity(createUserRequest, MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
//        String userDto = response.readEntity(String.class);
//        assertThat(userDto.getUserId(), equalTo(database.getById(userDto.getUserId()).getUserId()));

//        assertNotNull(createdEntity.getUsername());
//        assertNotNull(createdEntity.getId());
    }


    private void setUp() {
        String email = RandomStringUtils.randomAlphanumeric(5) + "@mail.ru";
        String username = RandomStringUtils.randomAlphanumeric(8);
        createUserRequest = new CreateUserRequest("mackevak1i", "deceiver1337", "deceiver1337", "yuyuyu@hf.yi", "9345884473");//(email, "qwerty", email, String.valueOf(RandomUtils.nextLong()));
    }
}
