package handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class SERVER {
    // The maximum number of waiting incoming connections to queue.
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    /**
     * This method initializes and runs the server.
     * @param portNumber The "portNumber" parameter specifies the port number on which the server should accept incoming client connections.
     */
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");

        try {
            // Create a new HttpServer object.
            // Rather than calling "new" directly, we instead create
            // the object by calling the HttpServer.create static factory method.
            // Just like "new", this method returns a reference to the new object.
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        server.createContext("/", new WebSiteHandler());
        server.createContext("/index.html", new WebSiteHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No arguments provided. You must provide port number.");
        }
        else {
            String portNumber = args[0];
            new SERVER().run(portNumber);
        }
    }
}