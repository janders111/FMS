package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.server.ExportException;

import javax.xml.ws.Response;

import model.User;
import service.LoadService;
import service.RegisterService;
import service.ReqAndResponses.LoadRequest;
import service.ReqAndResponses.RegisterResponse;
import service.ReqAndResponses.ResponseMessage;

public class LoadHandler extends ObjEncoderDecoder implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        LoadRequest requestObj = null;
        ResponseMessage response = null;
        System.out.println("LoadHandler called");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") == false) {
                throw new IOException("ERROR: Expected a POST request.");
            }
            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                try {
                    String requestString = getJson(exchange);
                    Gson gson = new Gson();
                    requestObj = gson.fromJson(requestString, LoadRequest.class);
                }
                catch (Exception e) {
                    throw new ExportException("Error reading json.");
                }
                response = LoadService.load(requestObj);
            }
            catch (Exception e) {
                response = new ResponseMessage(e.toString(), true);
            }
            sendJsonResponse(exchange, response);
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
