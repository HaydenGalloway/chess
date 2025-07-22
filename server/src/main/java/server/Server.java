package server;

import dataaccess.*;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDAO userDAO = new MySqlUserDAO();
        AuthDAO authDAO = new MySqlAuthDAO();
        GameDAO gameDAO = new MySqlGameDAO();

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

        Spark.post("/user", registerHandler::handleRegister);
        Spark.post("/session", loginHandler::handleLogin);
        Spark.delete("/session", logoutHandler::handleLogout);
        Spark.post("/game", createGameHandler::handleCreateGame);
        Spark.get("/game", listGamesHandler::handleListGames);
        Spark.put("/game", joinGameHandler::handleJoinGame);
        Spark.delete("/db", clearHandler::handelClear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
