package sailorsmarketplace;

import com.company.sailorsmarketplace.Launcher;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class UserServiceTest {
    private UserDAO database = new UserDAO();

    @Before
    public void startServer() throws Exception {
        Launcher.startServer();
    }

    @After
    public void stopServer() throws Exception {
        Launcher.stopServer();
    }

    @Test
    public void getByIdTest() {
        Long userId = 10L;
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
    public void getUserByEmailTest() {
        String findEmail = "test@test.com";
        User user = database.getByEmail(findEmail);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void deleteUserTest() {
        Long userId = 1L;
        User user = database.getById(userId);
        System.out.println(user.toString());
        database.delete(user);
        assertNull(database.getById(userId));
    }

    @Test
    public void updateUserTest() {
        Long userId = 8L;
        User userUpdate = database.getById(userId);
        userUpdate.setPassword("fghdjjiks1");
        database.update(userUpdate);

        assertEquals(database.getById(userId).getPassword(), userUpdate.getPassword());
    }

    @Test
    public void saveUserTest() {
        User user = new User(
                "username1337",
                "password1332",
                "dg3dyu@dghs.ru",
                "+79984536128"
        );

        User createdUser = database.save(user);
        assertThat(database.getById(createdUser.getUserId()).getUserId(), equalTo(createdUser.getUserId()));
    }

}
