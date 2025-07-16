package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.ClearService;
import spark.*;

import java.util.Map;

public class ClearHandler {

    private final ClearService clearService;
    private final Gson gson = new Gson();

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public Object handelClear (Request request, Response response) {
        try {
            clearService.clearAllData();
            response.status(200);
            return "{}";
        } catch (DataAccessException e) {
            response.status(500);
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
