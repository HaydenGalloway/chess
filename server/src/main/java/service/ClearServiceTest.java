package service;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClearServiceTest {

    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private ClearService clearService;

    @BeforeEach
    public void setUp() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        clearService = new ClearService(userDAO, authDAO, gameDAO);
    }

    @Test
    public void clearAllDataTest() throws DataAccessException {
        userDAO.createUser(new UserData("Kieth123", "KiethIsSuperCoool", "Kieth99@icloud.com"));
        assertDoesNotThrow(() -> clearService.clearAllData());
        assertThrows(DataAccessException.class, () -> userDAO.getUser("Kieth123"));
    }
}
