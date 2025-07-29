package client;

import model.GameData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {

    private final ServerFacade serverFacade;
    private String authToken;
    private List<GameData> games = new ArrayList<>();


    public Session(ServerFacade facade) {
        this.serverFacade = facade;
    }

    public ServerFacade getServerFacade() {
        return serverFacade;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String token) {
        this.authToken = token;
    }

    public void clearAuth() {
        this.authToken = null;
    }

    public int gameIdFromDisplayIndex(int displayIndex) {
        if (games == null || games.isEmpty()) {
            throw new IllegalArgumentException("Please 'list' games first.");
        }
        if (displayIndex < 1 || displayIndex > games.size()) {
            throw new IllegalArgumentException("Not a valid Game Number.");
        }
        var game = games.get(displayIndex - 1);
        return game.gameID();
    }

}
