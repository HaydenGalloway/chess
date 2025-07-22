package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
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
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (gameName == null) {
            throw new DataAccessException("Error: bad request");
        }
        ChessGame newChessGame = new ChessGame();
        newChessGame.getBoard().resetBoard();
        GameData newGame = new GameData(0, null, null, gameName, newChessGame);
        return gameDAO.createGame(newGame);
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        if (authDAO.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDAO.getAllGames();
    }

    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData gameData = gameDAO.getGame(gameID);
        if (gameData == null) {
            throw new DataAccessException("Error: bad request");
        }
        GameData activeGame;
        if ("WHITE".equalsIgnoreCase(playerColor)) {
            if (gameData.whiteUsername() != null) {
                throw new DataAccessException("Error: already taken");
            }
            activeGame = new GameData(gameID, authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        } else if ("BLACK".equalsIgnoreCase(playerColor)) {
            if (gameData.blackUsername() != null) {
                throw new DataAccessException("Error: already taken");
            }
            activeGame = new GameData(gameID, gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
        } else {
            throw new DataAccessException("Error: bad request");
        }
        gameDAO.updateGame(gameID, activeGame);
    }

}
