package client;

public class Session {

    private final ServerFacade serverFacade;
    private String authToken;


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
}
