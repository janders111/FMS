package service;

import com.google.gson.Gson;
import junit.framework.TestCase;
import java.sql.Connection;
import java.util.ArrayList;
import db.DBConnManager;
import db.EventDAO;
import db.PersonDAO;
import db.UserDAO;
import model.Person;
import service.ReqAndResponses.LoadRequest;
import service.ReqAndResponses.ResponseMessage;

public class LoadServiceTest extends TestCase {
    LoadRequest requestObj;
    String json = "" +
            "{\n" +
            "  \"users\": [\n" +
            "    {\n" +
            "      \"userName\": \"sheila\",\n" +
            "      \"password\": \"parker\",\n" +
            "      \"email\": \"sheila@parker.com\",\n" +
            "      \"firstName\": \"Sheila\",\n" +
            "      \"lastName\": \"Parker\",\n" +
            "      \"gender\": \"f\",\n" +
            "      \"personID\": \"Sheila_Parker\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"persons\": [\n" +
            "    {\n" +
            "      \"firstName\": \"Sheila\",\n" +
            "      \"lastName\": \"Parker\",\n" +
            "      \"gender\": \"f\",\n" +
            "      \"personID\": \"Sheila_Parker\",\n" +
            "      \"father\": \"Patrick_Spencer\",\n" +
            "      \"mother\": \"Im_really_good_at_names\",\n" +
            "      \"descendant\": \"sheila\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"firstName\": \"Patrick\",\n" +
            "      \"lastName\": \"Spencer\",\n" +
            "      \"gender\": \"m\",\n" +
            "      \"personID\":\"Patrick_Spencer\",\n" +
            "      \"spouse\": \"Im_really_good_at_names\",\n" +
            "      \"descendant\": \"sheila\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"firstName\": \"CS240\",\n" +
            "      \"lastName\": \"JavaRocks\",\n" +
            "      \"gender\": \"f\",\n" +
            "      \"personID\": \"Im_really_good_at_names\",\n" +
            "      \"spouse\": \"Patrick_Spencer\",\n" +
            "      \"descendant\": \"sheila\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"events\": [\n" +
            "    {\n" +
            "      \"eventType\": \"started family map\",\n" +
            "      \"personID\": \"Sheila_Parker\",\n" +
            "      \"city\": \"Salt Lake City\",\n" +
            "      \"country\": \"United States\",\n" +
            "      \"latitude\": 40.7500,\n" +
            "      \"longitude\": -110.1167,\n" +
            "      \"year\": 2016,\n" +
            "      \"eventID\": \"Sheila_Family_Map\",\n" +
            "      \"descendant\":\"sheila\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"eventType\": \"fixed this thing\",\n" +
            "      \"personID\": \"Patrick_Spencer\",\n" +
            "      \"city\": \"Provo\",\n" +
            "      \"country\": \"United States\",\n" +
            "      \"latitude\": 40.2338,\n" +
            "      \"longitude\": -111.6585,\n" +
            "      \"year\": 2017,\n" +
            "      \"eventID\": \"I_hate_formatting\",\n" +
            "      \"descendant\": \"sheila\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    public void setUp() throws Exception {
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        super.setUp();
        Gson gson = new Gson();
        requestObj = gson.fromJson(json, LoadRequest.class);
    }

    public void testLoadValid() throws Exception {
        LoadService.load(requestObj); //loads the objects in the long string above

        Connection conn = DBConnManager.getConnection();
        int numEvents = EventDAO.getNumEvents("sheila", conn);
        DBConnManager.closeConnection(conn, true);
        assertEquals("Add 2 events failed", numEvents, 2);

        ArrayList<Person> peopleArr = PersonDAO.getUsersPeoples("sheila");
        assertEquals("Add 3 people failed", peopleArr.size(), 3);

        assertTrue("Failed to add 1 user", UserDAO.usernameExists("sheila"));
    }

    public void testLoadInvalidObj() throws Exception {
        Boolean threw = false;
        Person invalidPerson = new Person(null, "d", "f",
                                "l", "f", "m", "f", "");
        Person personArr[] = new Person[1];
        personArr[0] = invalidPerson;
        LoadRequest req = new LoadRequest(null, personArr, null); //it is ok for
                                                            // the other two arrays to be null.

        ResponseMessage res = (ResponseMessage)LoadService.load(req);
        assertTrue("Did not return error when constraint was violated.", res.isError());
    }
}