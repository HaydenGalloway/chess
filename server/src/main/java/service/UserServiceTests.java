package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    private AuthDAO authDAO;
    private UserDAO userDAO;
    private UserService userService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    public void registerPositiveTest() {
        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        AuthData authData = assertDoesNotThrow(() -> userService.register(testUser), "Verifying that no exceptions are thrown.");
        assertNotNull(authData, "We should have something other than null here.");
        assertEquals(testUser.username(), authData.username(), "Usernames should be the same.");
    }

    @Test
    public void registerNegativeTest() {
        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        assertDoesNotThrow(() -> userService.register(testUser));
        DataAccessException exception = assertThrows(DataAccessException.class,
                () -> userService.register(testUser), "DataAccessException should be thrown.");
        assertEquals("Error: username already taken", exception.getMessage());
    }

    @Test
    public void loginPositiveTest() throws DataAccessException {
        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        userService.register(testUser);
        AuthData authData = assertDoesNotThrow(() -> userService.login(testUser),"Verifying that no exceptions are thrown.");
        assertNotNull(authData, "We should have something other than null here.");
        assertEquals(testUser.username(), authData.username(), "Usernames should be the same.");
    }

    @Test
    public void loginNegativeTest() throws DataAccessException {
        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        userService.register(testUser);
        UserData testWrongPassword = new UserData("Freddy", "Freddy#1234455676", "FreddyCoool@gmail.com");
        DataAccessException exception = assertThrows(DataAccessException.class,
                () -> userService.login(testWrongPassword), "DataAccessException should be thrown.");
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void logoutPositiveTest() throws DataAccessException {
        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        AuthData authData = userService.register(testUser);
        assertDoesNotThrow(() -> userService.logout(authData.authToken()), "Verifying that no exceptions are thrown.");
    }

    @Test
    public void logoutNegativeTest() {
        String badToken = "57593048qgh43eu9ehjee";
        DataAccessException exception = assertThrows(DataAccessException.class,
                () -> userService.logout(badToken), "DataAccessException should be thrown.");
        assertEquals("Error: unauthorized", exception.getMessage());
    }

}
