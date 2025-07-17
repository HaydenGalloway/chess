package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    GameData createGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Collection<GameData> getAllGames() throws DataAccessException;

    void updateGame(int gameID, GameData game) throws DataAccessException;

    void clear() throws DataAccessException;

}
