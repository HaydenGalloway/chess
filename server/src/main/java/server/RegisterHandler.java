package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.*;

import java.util.Map;

public class RegisterHandler {

    private final UserService userService;
    private final Gson gson = new Gson();

    public RegisterHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleRegister(Request request, Response response) {
        try {
            UserData user = gson.fromJson(request.body(), UserData.class);
            if (user.username() == null || user.password() == null || user.email() == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error: missing registration information"));
            }
            AuthData authData = userService.register(user);
            response.status(200);
            return gson.toJson(authData);
        } catch (DataAccessException e) {
            if ("Error: username already taken".equals(e.getMessage())) {
                response.status(403);
            } else {
                response.status(500);
            }
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
