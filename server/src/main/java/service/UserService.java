package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData register(UserData user) throws DataAccessException {
        userDAO.createUser(user);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, user.username());
        authDAO.createAuth(authData);
        return authData;
    }

    public AuthData login(UserData user) throws DataAccessException {
        UserData existingUser = userDAO.getUser(user.username());
        if (existingUser == null || !BCrypt.checkpw(user.password(), existingUser.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, user.username());
        authDAO.createAuth(authData);
        return authData;
    }

    public void logout(String authToken) throws DataAccessException {
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        authDAO.deleteAuth(authToken);
    }

}
