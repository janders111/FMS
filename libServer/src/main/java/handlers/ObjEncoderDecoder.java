package handlers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;

import model.*;

import static db.PersonDAO.createPerson;

public class ObjEncoderDecoder {
    public String objToJson(Object o) throws IOException {
        if(o == null) {
            throw new IOException("Input is null");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }

    public void sendJsonResponse(HttpExchange exchange, Object o) throws IOException {
        OutputStream os = exchange.getResponseBody();
        DataOutputStream dos = new DataOutputStream(os);
        //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); fixme
        dos.writeBytes(objToJson(o));
        dos.flush();
        dos.close();
    }

    public String getJson(HttpExchange exchange) throws IOException{
        InputStream is = exchange.getRequestBody();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        String requestString = buf.toString("UTF-8");
        //System.out.println(requestString);
        return requestString;
    }

    /**
     * Since users are stored in different tables from people, when we make the family history data,
     * we must create a person from the user object.
     *
     * @param u
     * @param momID
     * @param dadID
     * @param conn
     * @return
     * @throws Exception
     */
    public static Person createPersonFromUser(User u, String momID, String dadID, Connection conn) throws Exception {
        String spouse = null;
        Person p = new Person(u.getPersonID(), u.getUserName(), u.getFirstName(), u.getLastName(),
                u.getGender(), momID, dadID, spouse);
        createPerson(p, conn);
        return p;
    }

    public static boolean isDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
