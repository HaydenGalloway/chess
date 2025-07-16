package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.Map;

public class ListGamesHandler {

    private final GameService gameService;
    private final Gson gson = new Gson();

    public ListGamesHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handleListGames(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            Collection<GameData> games = gameService.listGames(authToken);
            response.status(200);
            return gson.toJson(Map.of("games", games));
        } catch (DataAccessException e) {
            response.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }
    }

}
