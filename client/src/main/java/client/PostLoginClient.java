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
               - play <game-number> <white|black> - play a game
               - observe <game-number> - observe a game
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
}
