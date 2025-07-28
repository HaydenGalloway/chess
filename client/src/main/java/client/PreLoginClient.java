package client;

import exception.ResponseException;
import model.AuthData;

import java.util.Arrays;

public class PreLoginClient implements ChessClient {

    private final Session session;

    public PreLoginClient(Session session) {
        this.session = session;
    }

    @Override
    public String help() {
        return """
               
               Welcome to Chess. Sign in to start.
               Options:
               - help
               - register <username> <password> <email>
               - login <username> <password>
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
            case "register" -> {
                if (tokens.length != 4) {
                    yield "Use the following format for 'register' command: \nregister <username> <password> <email> \n";
                }
                var username = params[1];
                var password = params[2];
                var email = params[3];
                try {
                    AuthData authData = session.getServerFacade().register(username, password, email);
                    session.setAuthToken(authData.authToken());
                    yield "Registered and logged in as '%s'.\n".formatted(username);
                } catch (ResponseException ex) {
                    yield "Registration failed: %s\n".formatted(ex.getMessage());
                }
            }
            case "login" -> {
                if (tokens.length != 3) {
                    yield "Use the following format for 'login' command:\nlogin <username> <password>\n";
                }
                var username = params[1];
                var password = params[2];
                try {
                    AuthData auth = session.getServerFacade().login(username, password);
                    session.setAuthToken(auth.authToken());
                    yield "Logged in as '%s'.\n".formatted(username);
                } catch (ResponseException ex) {
                    yield "Login failed: %s\n".formatted(ex.getMessage());
                }
            }
            default -> "Unable to process command. Type 'help' for options.\n";
        };
    }

}
