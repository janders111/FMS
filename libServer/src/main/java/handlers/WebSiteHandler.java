package handlers;

import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

class WebSiteHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // Now that the status code and headers have been sent to the client,
                // next we send the JSON data in the HTTP response body.

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();

                File file = new File("web" + File.separator + "index.html");

                Reader input = null;
                Writer output = null;
                try {
                    input = new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(file)));

                    output = new OutputStreamWriter(respBody);
                    output.write("HTTP/1.1 200 OK\n");
                    output.write("Content-type: text/html\n");

                    char[] chunk = new char[512];

                    int charsRead;
                    while ((charsRead = input.read(chunk)) > 0) {
                        output.write(chunk, 0, charsRead);
                    }
                    respBody.close();
                }
                finally {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    if (respBody != null) {
                        respBody.close();
                    }
                }
                /*
                try{
                    PrintWriter out = new PrintWriter(respBody);
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-type: text/html");
                    out.println("\r\n");
                    //out.println(System.getProperty("user.dir"));

                    // Open the file (may throw FileNotFoundException)
                    InputStream f = new FileInputStream("web" + File.separator + "index.html");
                    //InputStream f = this.getClass().getClassLoader()
                    //        .getResourceAsStream("web" + File.separator + "index.html");
                    String s = new BufferedReader(new InputStreamReader(f))
                            .lines().collect(Collectors.joining("\n"));
                    out.write(s);
                    /*
                    char[] chunk = new char[512];
                    int charsRead;
                    while ((charsRead = f.read(chunk)) > 0) {
                        out.write(chunk, 0, charsRead);
                    }*/
/*
                    out.flush();
                    out.close();

                    // Write the JSON string to the output stream.
                    writeString(respData, respBody);
                    // Close the output stream.  This is how Java knows we are done
                    // sending data and the response is complete/
                    respBody.close();
                } catch(Exception e) {
                    System.out.println(e);
                }
*/
                success = true;
            }

            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}