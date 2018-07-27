package service;

import junit.framework.TestCase;
import java.sql.Connection;

import javax.xml.ws.Response;

import db.DBConnManager;
import model.Event;
import model.Person;
import model.User;
import service.ReqAndResponses.EventRequest;
import service.ReqAndResponses.PersonRequest;
import service.ReqAndResponses.RegisterResponse;
import service.ReqAndResponses.ResponseMessage;

public class PersonServiceTest extends TestCase {
    RegisterResponse res = null;

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        User u = new User("username1", "password1",
                "e", "j", "a", "m", "id1");
        User u2 = new User("username", "password",
                "e", "j", "a", "m", "id");
        RegisterService.register(u);
        res = (RegisterResponse)RegisterService.register(u2);
    }

    public void testPersonValid() {
        PersonRequest req = new PersonRequest(res.getPersonID(), res.getToken());
        Person ret = (Person)PersonService.Person(req);

        assertTrue("Person() did not return right person.",
                res.getPersonID().equals(ret.getPersonID()));

        assertTrue("Person() did not return right person.",
                res.getUsername().equals(ret.getDescendant()));
    }

    public void testPersonNonValid() {
        PersonRequest invalidTokenRequest = new PersonRequest(res.getPersonID(), "asdf");
        ResponseMessage errorRes = (ResponseMessage)PersonService.Person(invalidTokenRequest);
        assertTrue("Person() should have given error to invalid authToken", errorRes.isError());

        PersonRequest invalidPersonIDRequest = new PersonRequest("t", res.getToken());
        errorRes = (ResponseMessage)PersonService.Person(invalidTokenRequest);
        assertTrue("Person() should have given error due to invalid eventID", errorRes.isError());
    }
}