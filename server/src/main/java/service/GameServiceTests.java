package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    AuthDAO authDAO;
    GameDAO gameDAO;
    private GameService gameService;
    private String authToken;

    @BeforeEach
    public void setUp() throws DataAccessException {

        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
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
    public void listGamesPositiveTest() {
        assertDoesNotThrow(() -> gameService.listGames(authToken),"Verifying that no exceptions are thrown.");
    }

    @Test
    public void listGamesNegativeTest() {
        String fakeToken = "57593048qgh43eu9ehjee";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> gameService.listGames(fakeToken),
                "DataAccessException should be thrown.");
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void joinGamePositiveTest() throws DataAccessException {
        GameData game = gameService.createGame(authToken, "Game To Join Name");
        assertDoesNotThrow(() -> gameService.joinGame(authToken, "WHITE", game.gameID()),
                "Verifying that no exceptions are thrown.");
        GameData updatedGame = gameDAO.getGame(game.gameID());
        assertEquals("Freddy", updatedGame.whiteUsername(), "The white player username should be 'Freddy'.");
    }

    @Test
    public void joinGameNegativeTest() throws DataAccessException {
        GameData game = gameService.createGame(authToken, "Game to Join Name");
        gameService.joinGame(authToken, "BLACK", game.gameID());
        DataAccessException exception = assertThrows(DataAccessException.class, () ->
                gameService.joinGame(authToken, "BLACK", game.gameID()),
                "DataAccessException should be thrown.");
        assertEquals("Error: already taken", exception.getMessage());
    }

}
