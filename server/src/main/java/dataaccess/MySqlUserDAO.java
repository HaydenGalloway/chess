package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;


public class MySqlUserDAO extends MySqlDAO implements UserDAO {

    public MySqlUserDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        if (getUser(user.username()) != null) {
            throw new DataAccessException("Error: already taken");
        }
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        var statement = "INSERT INTO users (username, password, email) VALUES (?,?,?)";
        executeUpdate(statement, user.username(), hashedPassword, user.email());
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Error: %s", e.getMessage()), e);
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE users";
        executeUpdate(statement);
    }

}