package db;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.SQLException;

import model.Event;
import model.Person;

import static org.junit.Assert.assertTrue;

public class EventDAOTest extends TestCase {
    Event e1;
    Event e2;
    Event e3;

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);

        e1 = new Event("id1", "d", "13", 32, 21,
                                        "USA", "provo", "birth", 1992);
        e2 = new Event("id2", "d", "42", 12, 31,
                "USA", "provo", "death", 1990);
        e3 = new Event("id3", "d", "45", 13, 32,
                "USA", "provo", "marriage", 1997);

        EventDAO.createEvent(e1, conn);
        EventDAO.createEvent(e2, conn);
        EventDAO.createEvent(e3, conn);
        DBConnManager.closeConnection(conn, true);
    }

    @org.junit.Test
    public void testGetEventException() throws Exception{
        Event result = null;
        Boolean threw = false;

        try {
            result = EventDAO.getEvent("12345");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getEvent did not throw when it was given a non-existant id", threw);

        threw = false;
        try {
            result = EventDAO.getEvent(null);
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getEvent did not throw when it was given a null id", threw);
    }

    public void testGetEvent() throws Exception{
        Event result = EventDAO.getEvent("id1");
        assertTrue("getEvent did not get right event e1", e1.equals(result));
        result = EventDAO.getEvent("id2");
        assertTrue("getEvent did not get right event e2", e2.equals(result));
        result = EventDAO.getEvent("id3");
        assertTrue("getEvent did not get right event e3", e3.equals(result));

    }

    public void testGetUsersEvents() {

    }

    public void testCreateEvent() {
    }

    public void testDeleteEvent() {
    }

    public void testDeleteAllEvents() {
    }

    public void testGetNumEvents() {
    }
}