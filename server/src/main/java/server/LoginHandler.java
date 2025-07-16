package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.*;

import java.util.Map;

public class LoginHandler {
    private final UserService userService;
    private final Gson gson = new Gson();

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleLogin(Request request, Response response) {
        try {
            UserData user = gson.fromJson(request.body(), UserData.class);
            if (user.username() == null || user.password() == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error: bad request"));
            }
            AuthData authData = userService.login(user);
            response.status(200);
            return gson.toJson(authData);
        } catch (DataAccessException e) {
            if ("Error: unauthorized".equals(e.getMessage())) {
                response.status(401);
                return gson.toJson(Map.of("message", e.getMessage()));
            }
            response.status(500);
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
