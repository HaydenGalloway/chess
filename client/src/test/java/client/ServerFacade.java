package client;

import com.google.gson.Gson;
import exception.ErrorResponse;
import exception.ResponseException;
import model.AuthData;
import model.UserData;


import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public record LoginRequest(String username, String password) {};

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData register(String username, String password, String email) throws  ResponseException {
        var path = "/user";
        var request = new UserData(username, password, email);
        return makeRequest("POST", path, request, AuthData.class, null);
    }

    public AuthData login(String username, String password) throws ResponseException {
        var path = "/session";
        var request = new LoginRequest(username, password);
        return makeRequest("POST", path, request, AuthData.class, null);
    }

    public AuthData logout(String authToken) throws ResponseException {
        var path = "/session";
        return makeRequest("DELETE", path, null, null, authToken);
    }


    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (authToken != null) {
                http.addRequestProperty("authorization", authToken);
            }
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
