package dataaccess;

import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlGameDAO implements GameDAO {
    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        var statement = "INSERT INTO games (gameName, game) VALUES (?,?)";
        var gson = new Gson();
        String gameJson = gson.toJson(game.game());
        int gameID = executeUpdate(statement, game.gameName(), gameJson);
        return new GameData(gameID, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(int gameID, GameData game) throws DataAccessException {
        var gson = new Gson();
        String gameJson = gson.toJson(game.game());
        var statement = "UPDATE games SET whiteUsername=?, blackUsername=?, game=? WHERE gameID=?";
        executeUpdate(statement, game.whiteUsername(), game.blackUsername(), gameJson, gameID);
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
