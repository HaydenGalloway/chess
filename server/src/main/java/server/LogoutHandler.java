package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.UserService;
import spark.*;

import java.util.Map;

public class LogoutHandler {

    private final UserService userService;
    private final Gson gson = new Gson();

    public LogoutHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleLogout(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            if (authToken == null) {
                response.status(401);
                return gson.toJson(Map.of("message", "Error: unauthorized"));
            }
            userService.logout(authToken);
            response.status(200);
            return "{}";
        } catch (DataAccessException e) {
            if ("Error: unauthorized".equals(e.getMessage())) {
                response.status(401);
            } else {
                response.status(500);
            }
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
