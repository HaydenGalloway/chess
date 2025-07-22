package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDAOTests {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MySqlAuthDAO();
        userDAO = new MySqlUserDAO();
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    public void createAuthPositive() throws DataAccessException {
        userDAO.createUser(new UserData("Freddy101", "MakeItGreatAgain", "elon@x.com"));
        AuthData testAuth = new AuthData("475$i3fhj@123", "Freddy101");
        assertDoesNotThrow(() -> authDAO.createAuth(testAuth), "Creating a valid authToken should succeed.");
    }

    @Test
    public void createAuthNegative() throws DataAccessException {
        AuthData testAuth = new AuthData("111112234", "testUser");
        authDAO.createAuth(testAuth);
        assertThrows(DataAccessException.class, () -> authDAO.createAuth(testAuth),
                "Creating a duplicate authToken should throw an exception.");
    }

    @Test
    public void getAuthPositive() throws DataAccessException {
        String tokenString = "qo38383c%ioa3838eh";
        AuthData originalAuth = new AuthData(tokenString, "Freddy102");
        authDAO.createAuth(originalAuth);
        AuthData returnedAuth = authDAO.getAuth(tokenString);
        assertNotNull(returnedAuth, "getAuth should not return null for a valid token.");
        assertEquals(originalAuth, returnedAuth, "returned AuthData does not match original.");
    }

}
