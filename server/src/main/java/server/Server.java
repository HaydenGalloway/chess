package server;

import dataaccess.*;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

import java.rmi.registry.Registry;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        UserService userService = new UserService(userDAO, authDAO);
        GameService gameService = new GameService(authDAO, gameDAO);
        ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

        RegisterHandler registerHandler = new RegisterHandler(userService);
        LoginHandler loginHandler = new LoginHandler(userService);
        LogoutHandler logoutHandler = new LogoutHandler(userService);

        CreateGameHandler createGameHandler = new CreateGameHandler(gameService);
        ListGamesHandler listGamesHandler = new ListGamesHandler(gameService);
        JoinGameHandler joinGameHandler = new JoinGameHandler(gameService);

        ClearHandler clearHandler = new ClearHandler(clearService);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", registerHandler::handleRegister);
        Spark.post("/session", loginHandler::handleLogin);
        Spark.delete("/session", logoutHandler::handleLogout);
        Spark.post("/game", createGameHandler::handleCreateGame);
        Spark.get("/game", listGamesHandler::handleListGames);
        Spark.put("/game", joinGameHandler::handleJoinGame);
        Spark.delete("/db", clearHandler::handelClear);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
