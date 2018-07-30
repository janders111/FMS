package service;

import junit.framework.TestCase;
import java.sql.Connection;
import db.AuthTokenDAO;
import db.DBConnManager;
import db.EventDAO;
import db.PersonDAO;
import db.UserDAO;
import model.AuthToken;
import model.Event;
import model.Person;

public class ClearServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        AuthToken a = new AuthToken("username1", "token1");
        AuthTokenDAO.createAuthToken(a, conn);
        Event e = new Event("e", "e", "e", 1, 1,
                "e", "e", "e", 1);
        EventDAO.createEvent(e, conn);
        Person p = new Person("p", "p", "p", "p",
                "m", "p", "p", "p");
        PersonDAO.createPerson(p, conn);
        DBConnManager.closeConnection(conn, true);
    }

    public void testClear() throws Exception{
        boolean threw = false;
        ClearService.clear();

        try {//check that authToken table got cleared
            AuthTokenDAO.getUsernameFromToken("token1");
        } catch (Exception ex){
            threw = true;
        }
        assertTrue("Did not clear authToken table", threw);
        threw = false;

        //check that events table got cleared
        Connection conn = DBConnManager.getConnection();
        int numEvents = EventDAO.getNumEvents("e", conn);
        DBConnManager.closeConnection(conn, true);
        assertEquals("Did not clear events table", numEvents, 0);
        threw = false;

        try {//check that persons table got cleared
            PersonDAO.getPerson("p", "e");
        } catch (Exception ex){
            threw = true;
        }
        assertTrue("Did not clear persons table", threw);

        //check that users table got cleared
        assertFalse("Did not clear users table", UserDAO.usernameExists("username1"));
    }
}