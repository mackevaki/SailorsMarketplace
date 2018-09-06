package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UsersResourceTest {
    private WebTarget target;

    @Before
    public void startServer() throws Exception {
        Launcher.runWebServer();

////        Client client = ClientBuilder.newClient();
////        target = client.target("http://localhost:9998/");
    }

    @After
    public void stopServer() throws Exception {
        Launcher.stopServer();
    }

    @Test
    public void createUserTest() throws Exception {
//        WebTarget userWebTarget = target.path("rest/accounts/reg");
        Launcher.runWebServer();

        final HttpUriRequest request = new HttpGet("http://localhost:9998/rest/accounts/count");

        final HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));

//        MultivaluedMap<String, String> data = new MultivaluedStringMap();
//        data.add("username", "qqq");
//        data.add("email", "qq@qq.qq");
//        data.add("password", "qqqqqqqqqqqqq");
//        data.add("telephone", "6347386343");

//        Invocation.Builder invocationBuilder = userWebTarget.request(MediaType.APPLICATION_JSON);
//        User user = new User(843984, "qqqq", "ds@sa.rt", "deccccccc", "583987438");
//        Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
//        User createdEntity = target.path("rest")
//                .path("accounts").path("reg")
//                .request()
//                .post(Entity.form(data))
//                .readEntity(User.class);

//        assertNotNull(createdEntity.getUsername());
//        assertNotNull(createdEntity.getId());
    }
}
