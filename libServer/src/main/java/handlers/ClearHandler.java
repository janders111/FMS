package handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import static service.ClearService.clear;

class ClearHandler extends ObjEncoderDecoder implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("ClearHandler called.");
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                sendJsonResponse(exchange, clear());
            }
            else{
                throw new IOException("ERROR: Expected a POST request.");
            }
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