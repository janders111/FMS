package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import service.PersonService;
import service.ReqAndResponses.PersonRequest;
import service.ReqAndResponses.ResponseMessage;

import static service.FillService.fill;

public class PersonHandler extends ObjEncoderDecoder implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String personID = null;
        Object response = null;
        String authToken = null;
        System.out.println("PersonHandler called.");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get") == false) {
                throw new IOException("ERROR: Expected a GET request.");
            }
            try {
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    authToken = reqHeaders.getFirst("Authorization");
                }
                else {
                    throw new IOException("Authorization token not found in header.");
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                URI uri = exchange.getRequestURI();
                String[] segments = uri.getPath().split("/");
                if (segments.length >= 3) {
                    personID = segments[2];
                } else if (segments.length == 2) {
                    //Return ALL family members of the current user.
                    personID = null;
                } else {
                    throw new IOException("Arguments incorrect. Example: /person/[personID] or /person");
                }
                PersonRequest requestObj = new PersonRequest(personID, authToken);
                response = PersonService.Person(requestObj);
            }
            catch (Exception e) {
                response = new ResponseMessage(e.toString(), true);
            }
            sendJsonResponse(exchange, response);
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
        finally {
            exchange.getResponseBody().close();
        }
    }
}