package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


}
