package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class EventResourceTest {
    private WebTarget target;

    @Inject
    private UserTestData userTestData;

    @Inject
    EventTestData eventTestData;

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
        // given
        User createdUser = userTestData.createTestUser();
        String eventName = "Event: " + randomAlphabetic(15, 45 - 8);
        String description = randomAlphabetic(20, 45);
        String  address = randomAlphabetic(15) + "street, " + randomNumeric(2);
        byte[] place = address.getBytes(StandardCharsets.UTF_8);
        CreateEventRequest createEventRequest = new CreateEventRequest(
                eventName,
                description,
                new Date(),
                new Date(),
                place,
                createdUser.getUserId());
        WebTarget userWebTarget = target.path("/rest/events/create");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder.post(Entity.entity(createEventRequest, MediaType.APPLICATION_JSON));

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldRemoveEventWhenThereAreAllCredentials() {
        // given
        final User user = userTestData.createTestUser();
        final Event event = eventTestData.createTestEvent(user);
        WebTarget userWebTarget = target.path("/rest/events/" + event.getEventId());
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder.delete();

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));

        // cleanup
        userTestData.removeTestUser(user);
    }
}
