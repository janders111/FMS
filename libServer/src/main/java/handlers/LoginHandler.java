package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.server.ExportException;

import model.User;
import service.ClearService;
import service.LoginService;
import service.RegisterService;
import service.ReqAndResponses.LoginRequest;
import service.ReqAndResponses.LoginResponse;

import static service.LoginService.login;

public class LoginHandler extends ObjEncoderDecoder implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Object response = null;
        LoginRequest loginRequestObj = null;
        System.out.println("LoginHandler called.");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") == false) {
                throw new IOException("ERROR: Expected a POST request.");
            }
            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                try {
                String requestString = getJson(exchange);
                Gson gson = new Gson();
                    loginRequestObj = gson.fromJson(requestString, LoginRequest.class);
                }
                catch (Exception e) {
                    throw new ExportException("Error reading json.");
                }
                response = login(loginRequestObj);
            } catch (Exception e) {
                response = new LoginResponse(null,null,null, e.toString());
            }
            sendJsonResponse(exchange, response);
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
        finally {
            exchange.getResponseBody().close();
        }
    }
}
