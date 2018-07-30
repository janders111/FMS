package db;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Event;
import model.Person;

import static org.junit.Assert.assertTrue;

public class EventDAOTest extends TestCase {
    //Event e1;
    Event e1 = new Event("id1", "d", "13", 32, 21,
                           "USA", "provo", "birth", 1992);
    Event e2 = new Event("id2", "d", "42", 12, 31,
                           "USA", "provo", "death", 1990);
    Event e3 = new Event("id3", "d", "45", 13, 32,
                           "USA", "provo", "marriage", 1997);

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        EventDAO.createEvent(e1, conn);
        EventDAO.createEvent(e2, conn);
        EventDAO.createEvent(e3, conn);
        DBConnManager.closeConnection(conn, true);
    }

    public void testGetEvent() throws Exception{
        Event result = EventDAO.getEvent("id1", "d");
        assertTrue("getEvent did not get right event e1", e1.equals(result));
        result = EventDAO.getEvent("id2", "d");
        assertTrue("getEvent did not get right event e2", e2.equals(result));
        result = EventDAO.getEvent("id3", "d");
        assertTrue("getEvent did not get right event e3", e3.equals(result));

    }

    @org.junit.Test
    public void testGetEventNonvalid() throws Exception{
        Event result = null;
        Boolean threw = false;

        try {
            result = EventDAO.getEvent("12345", "d");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getEvent did not throw when it was given a non-existant id", threw);

        threw = false;
        try {
            result = EventDAO.getEvent(null, "d");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getEvent did not throw when it was given a null id", threw);
    }

    /**
     * This tests the getUserEvents
     * It also tests the functionality of the createEvent function.
     *
     * @throws Exception
     */
    public void testGetUsersEvents() throws Exception{
        ArrayList<Event> eventsArr = EventDAO.getUsersEvents("d");
        assertEquals("getUserEvents returned wrong number of events", eventsArr.size(), 3);
    }

    public void testCreateEventNonvalid() throws Exception {
        Boolean threw = false;
        Connection conn = null;
        try {
            conn = DBConnManager.getConnection();
            EventDAO.createEvent(e1, conn);
        }
        catch(Exception e) {
            threw = true;
        }
        finally {
            DBConnManager.closeConnection(conn, true);
        }
        assertTrue("getUserEvents should have thrown because " +
                "this event is already in the db.", threw);
    }

    public void testGetUsersEventsNonvalid() throws Exception{
        Boolean threw = false;
        try {
            ArrayList<Event> eventsArr = EventDAO.getUsersEvents("asdf");
        }
        catch(Exception e) {
            threw = true;
        }
        assertTrue("getUserEvents should have thrown because " +
                                 "there are no events with this user", threw);
    }

    public void testDeleteUserseEvent() throws Exception {
        Connection conn = DBConnManager.getConnection();
        EventDAO.deleteUsersEvents("d", conn);
        DBConnManager.closeConnection(conn, true);
        Boolean threw = false;
        try {
             ArrayList<Event> eventsArr = EventDAO.getUsersEvents("d");
        }
        catch(Exception e) {
            threw = true;
        }
        assertTrue("getUserEvents should have thrown because " +
                "there are no events with this user", threw);

    }

    public void testDeletUserseEventNonvalid() throws Exception {
        Boolean threw = false;
        Connection conn = null;
        conn = DBConnManager.getConnection();
        EventDAO.deleteUsersEvents("asdf", conn);
        DBConnManager.closeConnection(conn, true);

        ArrayList<Event> eventsArr = EventDAO.getUsersEvents("d");
        assertEquals("DeleteUserEvents should not have deleted anything", eventsArr.size(), 3);
    }

    public void testGetNumEvents() throws Exception {
        Connection conn = DBConnManager.getConnection();
        int returnedNumEvents = EventDAO.getNumEvents("d", conn);
        DBConnManager.closeConnection(conn, true);
        assertEquals("getNumEvents returned the wrong number of events", returnedNumEvents, 3);
    }

    public void testGetNumEventsNonvalid() throws Exception {
        Connection conn = DBConnManager.getConnection();
        int returnedNumEvents = EventDAO.getNumEvents("asdf", conn);
        DBConnManager.closeConnection(conn, true);
        assertEquals("getNumEvents returned the wrong number of events", returnedNumEvents, 0);
    }
}