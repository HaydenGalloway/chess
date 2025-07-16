package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {

    private final Map<Integer, GameData> games = new HashMap<>();
    private int newGameID = 1;

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        GameData newGame = new GameData(newGameID, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(newGame.gameID(), newGame);
        newGameID++;
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new DataAccessException("Error: bad request");
        }
        return game;
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }
}
