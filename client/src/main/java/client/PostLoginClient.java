package client;

import exception.ResponseException;

import java.util.Arrays;

public class PostLoginClient implements ChessClient {

    public final Session session;
    public String username;

    public PostLoginClient(Session session) {
        this.session = session;
    }

    @Override
    public String help() {
        return """
               
               Options:
               - help
               - logout
               - create <game name> - create a new game
               - list - list the games
               - play <game number> <white|black> - play a game
               - observe <game number> - observe a game
               - quit
               
               """;
    }

    @Override
    public String eval(String command) throws Exception {
        if (command == null) {
            return "";
        }

        var tokens = command.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return switch (cmd) {
            case "help" -> help();
            case "quit" -> "quit";
            case "logout" -> logout();
            case "create" -> createGame(command);
            case "list" -> listGames();
            case "play" -> playGame(tokens);
            case "observe" -> observeGame(tokens);
            default -> "Unable to process command. Type 'help' for options.\n";
        };
    }

    private String logout() {
        try {
            session.getServerFacade().logout(session.getAuthToken());
        } catch (ResponseException ex) {
            session.clearAuth();
            return "Logged out: %s\n".formatted(ex.getMessage());
        }
        session.clearAuth();
        return "Logged out.\n";
    }

    private String createGame(String input) {
        var gameName = input.length() > "create".length() ? input.substring("create".length()).trim() : "";
        if (gameName.isEmpty()) {
            return "Use the following format for 'create' command:\ncreate <game name>\n";
        }
        try {
            session.getServerFacade().createGame(session.getAuthToken(), gameName);
            return "Game Created: '%s'.\n".formatted(gameName);
        } catch (ResponseException ex) {
            return "Failed to create a game: %s\n".formatted(ex.getMessage());
        }
    }

    private String listGames() {
        try {
            var gameList = session.getServerFacade().listGames(session.getAuthToken());
            return gameList.toString();
        } catch (ResponseException ex) {
            return "Failed to list games: %s\n".formatted(ex.getMessage());
        }
    }

    private String playGame(String[] tokens) {
        if (tokens.length != 3) {
            return "Use the following format for 'play' command:\nplay <game number> <white|black>\n";
        }
        int index;
        try {
            index = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            return "Game number must be an integer. Try 'list' then 'play <n> <white|black>'.\n";
        }
        var color = tokens[2];
        if (!color.equals("white") && !color.equals("black")) {
            return "Color must be 'white' or 'black'.\n";
        }
        int gameID = session.gameIdFromDisplayIndex(index);
        try {
            session.getServerFacade().joinGame(session.getAuthToken(), gameID, color.toUpperCase());
            // board logic
            return "Joined game as %s.\n".formatted(color);
        } catch (ResponseException ex) {
            return "Join failed: %s\n".formatted(ex.getMessage());
        }
    }

    private String observeGame(String[] tokens) {
        if (tokens.length != 2) {
            return "Use the following format for 'observe' command:\nobserve <game number>\n";
        }
        int index;
        try {
            index = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            return "Game number must be an integer. Try 'list' then 'observe <game number>'.\n";
        }
        int gameID;
        gameID = session.gameIdFromDisplayIndex(index);
        try {
            session.getServerFacade().joinGame(session.getAuthToken(), gameID, null);
            // board render logic
            return "Observing game.";
        } catch (ResponseException ex) {
            return "Failed to observe game: %s\n".formatted(ex.getMessage());
        }
    }



}
