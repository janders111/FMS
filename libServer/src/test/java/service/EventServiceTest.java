package service;

import junit.framework.TestCase;
import java.sql.Connection;
import db.DBConnManager;
import db.EventDAO;
import model.Event;
import model.User;
import service.ReqAndResponses.EventRequest;
import service.ReqAndResponses.RegisterResponse;
import service.ReqAndResponses.ResponseMessage;

public class EventServiceTest extends TestCase {
    RegisterResponse res = null;
    Event e1;
    Event e2;
    Event e3;

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        conn = DBConnManager.getConnection();
        User u = new User("username1", "password1",
                "e", "j", "a", "m", "id1");
        User u2 = new User("username", "password",
                "e", "j", "a", "m", "id");
        RegisterService.register(u2);
        res = (RegisterResponse)RegisterService.register(u);

        e1 = new Event("id1", "username1", "13", 32, 21,
                "USA", "provo", "birth", 1992);
        e2 = new Event("id2", "username1", "42", 12, 31,
                "USA", "provo", "death", 1990);
        e3 = new Event("id3", "username1", "45", 13, 32,
                "USA", "provo", "marriage", 1997);

        EventDAO.createEvent(e1, conn);
        EventDAO.createEvent(e2, conn);
        EventDAO.createEvent(e3, conn);
        DBConnManager.closeConnection(conn, true);
    }

    public void testEventValid() {
        EventRequest req = new EventRequest("id1", res.getToken());
        Event ret = (Event)EventService.event(req);

        assertTrue("Event did not return right Event.",
                ret.equals(e1));
    }

    public void testEventNonValid() {
        EventRequest invalidTokenRequest = new EventRequest("id1", "asdf");
        ResponseMessage errorRes = (ResponseMessage)EventService.event(invalidTokenRequest);
        assertTrue("Person() should have given error to invalid authToken", errorRes.isError());

        EventRequest invalidEventIDRequest = new EventRequest("t", res.getToken());
        errorRes = (ResponseMessage)EventService.event(invalidTokenRequest);
        assertTrue("Person() should have given error due to invalid eventID", errorRes.isError());
    }
}