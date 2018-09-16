package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.rest.CreateUpdateUserRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UsersResourceTest {
    private WebTarget target;
    private CreateUpdateUserRequest createUpdateUserRequest;
    private UserDAO database = new UserDAO();

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
    public void shouldCreateUserWhenAllInputsAreValid() {
        WebTarget userWebTarget = target.path("rest/accounts/reg");
        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(createUpdateUserRequest, MediaType.APPLICATION_JSON));
//        System.out.println("-----------  " + ((UserDto)response.getEntity()).getUsername());

        assertThat(response.getStatusInfo().getStatusCode(), equalTo(200));
        UserDto userDto = response.readEntity(UserDto.class);
        assertThat(userDto.getUserId(), equalTo(database.getById(userDto.getUserId()).getUserId()));

//        assertNotNull(createdEntity.getUsername());
//        assertNotNull(createdEntity.getId());
    }


    private void setUp() {
        String email = RandomStringUtils.randomAlphanumeric(5) + "@mail.ru";
        String username = RandomStringUtils.randomAlphanumeric(8);
    }
}
