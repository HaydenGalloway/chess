package server;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import service.UserService;
import spark.*;

import java.rmi.registry.Registry;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService userService = new UserService(userDAO, authDAO);

        RegisterHandler registerHandler = new RegisterHandler(userService);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", registerHandler::handleRegister);

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
