package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;

public class GameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public GameData createGame(String authToken, String gameName) throws DataAccessException {
        authDAO.getAuth(authToken);
        ChessGame newChessGame = new ChessGame();
        newChessGame.getBoard().resetBoard();
        GameData newGame = new GameData(0, null, null, gameName, newChessGame);
        return gameDAO.createGame(newGame);
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        authDAO.getAuth(authToken);
        return gameDAO.getAllGames();
    }

}
