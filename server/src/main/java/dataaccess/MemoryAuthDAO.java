package dataaccess;

import model.AuthData;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {

    private final Map<String, AuthData> authTokens = new HashMap<>();

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        if (authTokens.containsKey(authData.authToken())) {
            throw new DataAccessException("Error. Token previously generated.");
        }
        authTokens.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        AuthData authData = authTokens.get(authToken);
        if (authData == null) {
            throw new DataAccessException("Error. Not authorized.");
        }
        return authData;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (!authTokens.containsKey(authToken)) {
            throw new DataAccessException("Error. Not authorized.");
        }
        authTokens.remove(authToken);
    }

    @Override
    public void clear() throws DataAccessException {
        authTokens.clear();
    }
}
