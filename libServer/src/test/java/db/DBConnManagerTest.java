package db;

import junit.framework.TestCase;
import java.sql.Connection;
import model.AuthToken;
import model.Event;
import model.Person;

/**
 * there are no tests for getConnection since every test relies on that function to work
 */
public class DBConnManagerTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
    }

    /**
     * Tests clearTables and createTables
     * This function tests clearTables, which also recreates the tables.
     * @throws Exception
     */
    public void testClearTables() throws Exception{
        boolean threw = false;
        Connection conn = DBConnManager.getConnection();
        AuthToken a = new AuthToken("username1", "token1");
        AuthTokenDAO.createAuthToken(a, conn);
        Event e = new Event("e", "e", "e", 1, 1,
                                "e", "e", "e", 1);
        EventDAO.createEvent(e, conn);
        Person p = new Person("p", "p", "p", "p",
                                        "m", "p", "p", "p");
        PersonDAO.createPerson(p, conn);
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);

        try {//check that authToken table got cleared
            AuthTokenDAO.getUsernameFromToken("token1");
        } catch (Exception ex){
            threw = true;
        }
        assertTrue("Did not clear authToken table", threw);
        threw = false;

        conn = DBConnManager.getConnection();
        int numEvents = EventDAO.getNumEvents("e", conn);
        DBConnManager.closeConnection(conn, true);

        assertEquals("Did not clear events table", numEvents, 0);
        threw = false;

        try {//check that persons table got cleared
            PersonDAO.getPerson("p", "p");
        } catch (Exception ex){
            threw = true;
        }
        assertTrue("Did not clear persons table", threw);

        //check that users table got cleared
        assertFalse("Did not clear users table", UserDAO.usernameExists("username1"));
    }

    public void testCloseConnectionRollback() throws Exception{
        String returnedUsername;
        Boolean threw = false;
        Connection conn = DBConnManager.getConnection();
        AuthToken a1 = new AuthToken("username1", "token1");
        AuthTokenDAO.createAuthToken(a1, conn);
        AuthToken a2 = new AuthToken("username2", "token2");
        AuthTokenDAO.createAuthToken(a2, conn);
        DBConnManager.closeConnection(conn, false);

        try {
            returnedUsername = AuthTokenDAO.getUsernameFromToken("token1");
        } catch (Exception e){
            threw = true;
        }
        assertTrue("changes were not rolled back when they should have been", threw);
    }

    public void testCloseConnectionCommit() throws Exception{
        String returnedUsername;
        Connection conn = DBConnManager.getConnection();
        AuthToken a1 = new AuthToken("username1", "token1");
        AuthTokenDAO.createAuthToken(a1, conn);
        AuthToken a2 = new AuthToken("username2", "token2");
        AuthTokenDAO.createAuthToken(a2, conn);
        DBConnManager.closeConnection(conn, true);

        returnedUsername = AuthTokenDAO.getUsernameFromToken("token1");
        assertEquals("changes were not sucessfully comitted", returnedUsername, "username1");
        returnedUsername = AuthTokenDAO.getUsernameFromToken("token2");
        assertEquals("changes were not sucessfully comitted", returnedUsername, "username2");
    }

    public void testCheckConnectionNull() throws Exception{
        Boolean threw = false;
        Connection conn = DBConnManager.getConnection();
        try {
            DBConnManager.checkConnection(conn);
        }
        catch (Exception e){
            threw = true;
        }
        finally {
            DBConnManager.closeConnection(conn, true);
        }
        assertFalse("checkTestConnection threw when it was shown a valid connection", threw);
    }

    public void testCheckConnectionNullInvalid() {
        Boolean threw = false;
        Connection nullConn = null;
        try {
            DBConnManager.checkConnection(nullConn);
        }
        catch (Exception e){
            threw = true;
        }
        assertTrue("checkTestConnection did not throw when given a null connection ", threw);
    }
}