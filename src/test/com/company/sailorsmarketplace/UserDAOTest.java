package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.rest.CreateUserRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class UserDAOTest {
    private WebTarget target;
    private UserDAO database = new UserDAO();

    private Long userId;
    private String findEmail;
    private static UsersEntity usersEntity;

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
    public void getByIdTest() {
        UsersEntity user = database.getById(userId);
        assertNotNull(user);
        assertThat(user.getUserId(), equalTo(userId));
        System.out.println(user.toString());
    }

    @Test
    public void findAllTest() {
        List<UsersEntity> usersEntities = database.findAll();
        for (UsersEntity u : usersEntities) {
            System.out.println("---------------------------------");
            assertNotNull(u);
            System.out.println(u.toString());
        }
    }

    @Test
    public void getUserByEmailTest() throws Throwable {
        UsersEntity user = database.getByEmail(findEmail);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void deleteUserTest() {
        UsersEntity user = database.getById(userId);
        System.out.println(user.toString());
        database.delete(user);
        assertNull(database.getById(userId));
    }

    @Test
    public void updateUserTest() {
        UsersEntity userUpdate = database.getById(userId);
        userUpdate.setPassword("fghdjjiks1");
        database.update(userUpdate);

        assertEquals(database.getById(userId).getPassword(), userUpdate.getPassword());
    }

    @Test
    public void saveUserTest() throws Throwable {
        UsersEntity createdUser = database.save(usersEntity);
        assertThat(database.getById(createdUser.getUserId()).getUserId(), equalTo(createdUser.getUserId()));
    }

    private void setUp() {
        usersEntity = new UsersEntity();
        usersEntity.setUsername("p1337fvh");
        usersEntity.setTelephone("78642352");
        usersEntity.setPassword("hgj11ffsd");
        usersEntity.setEmail("fff@fdh.yu");
        usersEntity.setEnabled((byte)1);
        usersEntity.setSalt("ssssss");
        userId = 6L;
        findEmail = "ds@gb.uyu";
    }

}
