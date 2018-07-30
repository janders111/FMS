package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.server.ExportException;

import model.User;
import service.RegisterService;
import service.ReqAndResponses.RegisterResponse;

public class RegisterHandler extends ObjEncoderDecoder implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User newUser = null;
        Object response = null;
        System.out.println("RegisterHandler called");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") == false) {
                throw new IOException("ERROR: Expected a POST request.");
            }
            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                try {
                    String requestString = getJson(exchange);
                    Gson gson = new Gson();
                    newUser = gson.fromJson(requestString, User.class);
                }
                catch (Exception e) {
                    throw new ExportException("Error reading json.");
                }
                response = RegisterService.register(newUser);
            }
            catch (Exception e) {
                response = new RegisterResponse(null,null,null, e.toString());
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
