package sailorsmarketplace;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.config.Module;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.SourceSystem;
import com.company.sailorsmarketplace.requests.VerificationRequest;
import com.company.sailorsmarketplace.services.VerificationService;
import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
@GuiceModules(Module.class)
public class VerificationResourceTest {
    private WebTarget target;

    @Inject
    private UserTestData userTestData;

    @Inject
    private VerificationService serv;

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
        // given
        User createdUser = userTestData.createTestUser();
        Date date = new Date();
        String targetId = randomNumeric(11);
        Long targetUserId = createdUser.getUserId();
        SourceSystem sourceSystem = SourceSystem.MOBILE_PHONE;

        VerificationRequest request = new VerificationRequest(
                date,
                sourceSystem,
                targetId,
                targetUserId);

        WebTarget userWebTarget = target.path("/rest/verify/send");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        // when
        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));

        // then
        assertThat(response.getStatusInfo().getStatusCode(), equalTo(HttpStatus.SC_OK));

        // cleanup
        userTestData.removeTestUser(createdUser);

    }
}