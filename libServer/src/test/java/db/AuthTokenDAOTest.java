package db;

import junit.framework.TestCase;
import java.sql.Connection;
import model.AuthToken;

public class AuthTokenDAOTest extends TestCase {
    AuthToken a = new AuthToken("username1", "token1");
    AuthToken b = new AuthToken("username2", "token2");
    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        conn = DBConnManager.getConnection();
        AuthTokenDAO.createAuthToken(a, conn);
        AuthTokenDAO.createAuthToken(b, conn);
        DBConnManager.closeConnection(conn, true);
    }

    /**
     * tests both the createAuthToken and getUsernameFromToken functions.
     *
     * @throws Exception
     */
    public void testAuthTokenDAO() throws Exception{
        Connection conn = DBConnManager.getConnection();
        Boolean threw = false;
        //test getUsernameFromToken
        String returnedUsername = AuthTokenDAO.getUsernameFromToken("token1");
        assertEquals("getUsernameFromToken failed", returnedUsername, "username1");
        returnedUsername = AuthTokenDAO.getUsernameFromToken("token2");
        assertEquals("getUsernameFromToken failed", returnedUsername, "username2");
        DBConnManager.closeConnection(conn, true);
    }

    public void testThrowing() throws Exception{
        Boolean threw = false;
        Connection conn = DBConnManager.getConnection();

        //inserting a token with the same username should delete the original one.
        AuthToken c = new AuthToken("username", "tokenNew");
        AuthTokenDAO.createAuthToken(c, conn);
        AuthToken d = new AuthToken("username", "tokenNewer");
        AuthTokenDAO.createAuthToken(d, conn);
        DBConnManager.closeConnection(conn, true);
        String returnedUsername = AuthTokenDAO.getUsernameFromToken("tokenNewer");
        assertEquals("after inserting a newer token, the newer one was not returned.",
                returnedUsername, "username");
        try {
            returnedUsername = AuthTokenDAO.getUsernameFromToken("token");
        } catch (Exception e){
            threw = true;
        }
        assertTrue("after inserting a newer token, the old one was not overwritten.", threw);

        //inserting two of the same tokens
        threw = false;
        try {
            AuthToken e = new AuthToken("username1", "tokenNew");
            AuthTokenDAO.createAuthToken(a, conn);
        }
        catch (Exception e) {
            threw = true;
        }
        assertTrue("Did not throw an exception when inserting two of the same tokens", threw);

    }
}