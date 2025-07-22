package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTests {

    private UserDAO userDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new MySqlUserDAO();
        userDAO.clear();
    }

    @Test
    public void createUserPositive() {
        UserData testUser = new UserData("George101", "GeorgyPorgy", "george@gmail.com");
        assertDoesNotThrow(() -> userDAO.createUser(testUser), "Creating a user should not throw an exception.");
    }

    @Test
    public void createUserNegative() {
        UserData testUser = new UserData(null, "GeorgyPorgy2", "george@gmail.com");
        assertThrows(DataAccessException.class, () -> userDAO.createUser(testUser), "We expect a error from the null value.");
    }

    @Test
    public void getUserPositive() throws DataAccessException {
        UserData originalUser = new UserData("GeorgyPorgy3", "GeorgeIsCool!", "george3@gmail.com");
        userDAO.createUser(originalUser);
        UserData retrievedUser = userDAO.getUser("GeorgyPorgy3");
        assertNotNull(retrievedUser, "getUser should return something.");
        assertEquals(originalUser.username(), retrievedUser.username(), "Usernames should match.");
    }

    @Test
    public void getUserNegative() throws DataAccessException {
        UserData returnedUser = userDAO.getUser("FakeUser104");
        assertNull(returnedUser, "getUser should return null for a user that does not exist.");
    }

    @Test
    public void clearPositive() throws DataAccessException {
        userDAO.createUser(new UserData("Georgey101", "theOG1", "george1@gmail.com"));
        userDAO.clear();
        assertNull(userDAO.getUser("Georgey101"), "Georgey101 should not exist after clear.");
    }

}
