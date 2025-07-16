package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {

    private GameService gameService;
    private String authToken;

    @BeforeEach
    public void setUp() throws DataAccessException {

        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        UserDAO userDAO = new MemoryUserDAO();
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();

        gameService = new GameService(authDAO, gameDAO);
        UserService userService = new UserService(userDAO, authDAO);

        UserData testUser = new UserData("Freddy", "Freddy#1", "FreddyCoool@gmail.com");
        AuthData authData = userService.register(testUser);
        authToken = authData.authToken();
    }

    @Test
    public void createGamePositiveTest() {
        GameData newGame = assertDoesNotThrow(() -> gameService.createGame(authToken, "New Game Test"),
                "Verifying that no exceptions are thrown.");
        assertNotNull(newGame, "We expect the new game to not be null.");
        assertEquals("New Game Test", newGame.gameName(), "These should be the same name.");
    }

    @Test
    public void createGameNegativeTest() {
        String badToken = "57593048qgh43eu9ehjee";
        DataAccessException exception = assertThrows(DataAccessException.class, () ->
                gameService.createGame(badToken, "Bad Token Game"),"DataAccessException should be thrown.");
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void listGamesPositive() {
        assertDoesNotThrow(() -> gameService.listGames(authToken),"Verifying that no exceptions are thrown.");
    }

    @Test
    public void listGamesNegative() {
        String fakeToken = "57593048qgh43eu9ehjee";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> gameService.listGames(fakeToken),
                "DataAccessException should be thrown.");
        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
