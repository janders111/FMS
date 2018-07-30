package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.xml.ws.Response;

import service.ClearService;
import service.FillService;
import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.ResponseMessage;

import static service.FillService.fill;

public class FillHandler extends ObjEncoderDecoder implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String username = null;
        ResponseMessage response = null;
        int generations;
        System.out.println("FillHandler called.");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") == false) {
                throw new IOException("ERROR: Expected a POST request.");
            }
            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                //URL Path: /fill/[username]/{generations}
                //Example: /fill/susan/3
                URI uri = exchange.getRequestURI();
                String[] reqSegments = uri.getPath().split("/");
                if (reqSegments.length >= 4) {
                    username = reqSegments[2];
                    if (!isDigit(reqSegments[3])) {
                        throw new IOException("Third argument must be a number.");
                    }
                    try {
                        generations = Integer.parseInt(reqSegments[3]);
                    }
                    catch(NumberFormatException e) {
                        throw new NumberFormatException("Number Format Error");
                    }
                } else if (reqSegments.length == 3) {
                    username = reqSegments[2];
                    generations = 4; //this is the default number of generations
                } else {
                    throw new IOException("Arguments incorrect. Example: /fill/susan/3");
                }
                FillRequest fillRequestObj = new FillRequest(username, generations);
                response = fill(fillRequestObj);
            } catch (Exception e) {
                response = new ResponseMessage("Error: " + e.toString(), true);
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
