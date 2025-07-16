package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class JoinGameHandler {

    private final GameService gameService;
    private final Gson gson = new Gson();

    private record JoinRequest(String playerColor, Integer gameID) {}

    public JoinGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handleJoinGame(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            JoinRequest joinRequest = gson.fromJson(request.body(), JoinRequest.class);
            if (joinRequest.gameID == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error: bad request"));
            }
            gameService.joinGame(authToken, joinRequest.playerColor(), joinRequest.gameID);
            response.status(200);
            return "{}";
        } catch (DataAccessException e) {
            if ("Error: unauthorized".equals(e.getMessage())) {
                response.status(401);
            } else if ("Error: already taken".equals(e.getMessage())) {
                response.status(403);
            } else if ("Error: bad request".equals(e.getMessage())) {
                response.status(400);
            } else {
                response.status(500);
            }
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }

}
