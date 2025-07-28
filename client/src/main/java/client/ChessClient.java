package client;

public interface ChessClient {

    String help();

    String eval(String command) throws Exception;

}
