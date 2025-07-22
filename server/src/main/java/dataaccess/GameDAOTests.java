package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTests {

    private GameDAO gameDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new MySqlGameDAO();
        gameDAO.clear();
    }

    @Test
    public void createGamePositive() {
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(1, "Fred", "George", "BattleRoyal", chessGame);
        assertDoesNotThrow(() -> gameDAO.createGame(gameData), "We expect this game to be valid.");
    }

    @Test
    public void createGameNegative() {
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(2, null, null, null, chessGame);
        assertThrows(DataAccessException.class, () -> gameDAO.createGame(gameData),
                "We expect there to be an exception for a null gameName.");
    }

    @Test
    public void getAllGamesPositive() throws DataAccessException {
        gameDAO.createGame(new GameData(1, null, "Fred", "FirstGame", new ChessGame()));
        gameDAO.createGame(new GameData(2, "George", null, "SecondGame", new ChessGame()));
        gameDAO.createGame(new GameData(3, "Sally", "Nancy", "ThirdGame", new ChessGame()));
        Collection<GameData> games = gameDAO.getAllGames();
        assertEquals(3, games.size(), "We expect a collection of three games.");
    }

    @Test
    public void getAllGamesNegative() throws DataAccessException {
        Collection<GameData> games = gameDAO.getAllGames();
        assertNotNull(games, "Empty collection, but not null.");
        assertNotEquals(2, games.size(), "No games were added.");
    }

    @Test
    public void updateGamePositive() throws DataAccessException {
        GameData gameData = gameDAO.createGame(new GameData(1, null, null,
                "OriginalGame", new ChessGame()));
        int gameID = gameData.gameID();
        GameData updatedGame = new GameData(gameID, "whiteUsername", "blackUsername",
                "Original Game", gameData.game());
        gameDAO.updateGame(gameID, updatedGame);
        GameData newGameData = gameDAO.getGame(gameID);
        assertEquals("whiteUsername", newGameData.whiteUsername(), "White user name should be updated.");
        assertEquals("blackUsername", newGameData.blackUsername(), "Black user name should be updated.");
    }

    @Test
    public void clearPositive() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "whiteUsername", "blackUsername",
                "Original Game", new ChessGame()));
        gameDAO.clear();
        assertTrue(gameDAO.getAllGames().isEmpty(), "We expect an empty collection.");
    }

}
