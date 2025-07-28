package client;

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
        return "";
    }
}
