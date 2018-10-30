package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.SourceSystem;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.company.sailorsmarketplace.requests.VerificationRequest;
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
import java.time.LocalDate;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class VerificationResourceTest {
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
    public void shouldSendCodeWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("/rest/verify/send");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        User createdUser = testValues.createTestUser();

        Date date = Date.valueOf(LocalDate.now().toString());
        String targetId = randomNumeric(11);
        String targetUserId = String.valueOf(createdUser.getUserId());
        Enum<SourceSystem> sourceSystem = SourceSystem.MOBILE_PHONE;

        VerificationRequest request = new VerificationRequest(
                date,
                sourceSystem,
                targetId,
                targetUserId);

        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }
}