package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.EventDao;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.company.sailorsmarketplace.requests.CreateUserRequest;
import javassist.bytecode.ByteArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Arrays;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EventResourceTest {
    private WebTarget target;
    private EventDao database = new EventDao();

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
    public void shouldCreateEventWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("/rest/events/create");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        String eventName = "Event: " + randomAlphabetic(15);
        String description = randomAlphabetic(20);
        byte[] place = "Pervomayskaya street 34".getBytes(StandardCharsets.UTF_8);

        CreateEventRequest createEventRequest = new CreateEventRequest(
                eventName,
                description,
                new Date(3L),
                new Date(3L),
                place,
                17L);

        Response response = invocationBuilder.post(Entity.entity(createEventRequest, MediaType.APPLICATION_JSON));
        //Long createdEventId = Long.valueOf(response.readEntity(String.class));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));

//        System.out.println("--------------------------" + createdEventId);

//        UserProfileInfoService service = new UserProfileInfoService();
//        UserProfileInfo info = service.createUserProfileInfoForNewUser(createdUser.getUserId());
//        assertThat(info.getUserId(), equalTo(createdUser.getUserId()));
    }

}
