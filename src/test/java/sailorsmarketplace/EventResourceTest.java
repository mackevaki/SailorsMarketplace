package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.EventDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.company.sailorsmarketplace.utils.TestValues;
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
import java.sql.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class EventResourceTest {
    private WebTarget target;

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
    public void shouldCreateEventWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("/rest/events/create");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        User createdUser = testValues.createTestUser();

        String eventName = "Event: " + randomAlphabetic(15, 50);
        String description = randomAlphabetic(20, 100);
        String  address = randomAlphabetic(15) + "street, " + randomNumeric(2);
        byte[] place = address.getBytes(StandardCharsets.UTF_8);

        CreateEventRequest createEventRequest = new CreateEventRequest(
                eventName,
                description,
                new Date(3L),
                new Date(3L),
                place,
                createdUser.getUserId());

        Response response = invocationBuilder.post(Entity.entity(createEventRequest, MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

}
