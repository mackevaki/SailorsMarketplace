package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.dto.CreateUserRequest;
import com.company.sailorsmarketplace.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class UserDAOTest {
    private WebTarget target;
    private UserDAO database = new UserDAO();

//    @Inject
//    private IUserService userService;
    private UserService userService = new UserService();

    private Long userId;
    private String findEmail;
    private static User user;
    private CreateUserRequest createUserRequest;

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
        User user = database.getById(userId);
        assertNotNull(user);
        assertThat(user.getUserId(), equalTo(userId));
        System.out.println(user.toString());
    }

    @Test
    public void findAllTest() {
        List<User> usersEntities = database.findAll();
        for (User u : usersEntities) {
            System.out.println("---------------------------------");
            assertNotNull(u);
            System.out.println(u.toString());
        }
    }

    @Test
    public void getUserByEmailTest()  {
        User user = database.getByEmail(findEmail);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void deleteUserTest() {
        User user = database.getById(userId);
        System.out.println(user.toString());
        database.delete(user);
        assertNull(database.getById(userId));
    }

    @Test
    public void updateUserTest() {
        User userUpdate = database.getById(userId);
        userUpdate.setPassword("fghdjjiks1");
        database.update(userUpdate);

        assertEquals(database.getById(userId).getPassword(), userUpdate.getPassword());
    }

    @Test
    public void saveUserTest() {
        User createdUser = database.save(user);
        assertThat(database.getById(createdUser.getUserId()).getUserId(), equalTo(createdUser.getUserId()));
    }

    private void setUp() {
        user = new User();
        user.setUsername("p1337hgvh");
        user.setTelephone("786423352");
        user.setPassword("hgj11ffsd");
        user.setEmail("ffaf@fdh.yu");
        user.setEnabled((byte)1);
        user.setSalt("sssfsss");
        userId = 8L;
        findEmail = "ds@gb.uyu";
    }

    private void setUpCreateUserRequest() {

    }

    @Test
    public void createUserServiceTest() throws UserExistsException {
        createUserRequest = new CreateUserRequest(
                "hfudiek",
                "hgj11ffsd",
                "hgj11ffsd",
                "kira@mail.ru",
                "89356757323"
        );

        User res = userService.createNewUser(createUserRequest);
        System.out.println(res.toString());
        assertEquals(res.getUsername(), createUserRequest.username);

    }

}
