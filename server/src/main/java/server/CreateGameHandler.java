package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class CreateGameHandler {

    private final GameService gameService;
    private final Gson gson = new Gson();


    public CreateGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handleCreateGame(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            Map<String, String> gameNamesMap = gson.fromJson(request.body(), Map.class);
            String gameName = gameNamesMap.get("gameName");
            if (gameName == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error: bad request"));
            }
            GameData newGame = gameService.createGame(authToken, gameName);
            response.status(200);
            return gson.toJson(Map.of("gameID", newGame.gameID()));
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
