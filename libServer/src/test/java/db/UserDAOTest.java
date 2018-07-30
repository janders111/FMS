package db;

import junit.framework.TestCase;
import java.sql.Connection;
import model.User;

public class UserDAOTest extends TestCase {
    User u = new User("username", "pass", "emailll", "j",
                        "andersen", "m", "personIDDDDDD");

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        conn = DBConnManager.getConnection();
        UserDAO.createUser(u, conn);
        DBConnManager.closeConnection(conn, true);
    }
    public void testCreateUser() throws Exception{
        Connection conn = DBConnManager.getConnection();

        //try inserting a duplicate username. Should throw an exception
        Boolean threw = false;
        try {
            UserDAO.createUser(u, conn);
        }
        catch(Exception e) {
            threw = true;
        }
        assertTrue(threw);
    }

    public void testUsernameExists() throws Exception {
        Boolean exists = UserDAO.usernameExists("username");
        assertTrue("usernameExists returned false for existing username", exists);

        exists = UserDAO.usernameExists("usernameNo");
        assertFalse("usernameExists returned true for non-existing username", exists);
    }

    public void testGetUser() throws Exception {
        Boolean threw = false;
        try { //non-existant username
            Connection conn = DBConnManager.getConnection();
            User returnedUser = UserDAO.getUser("");
            DBConnManager.closeConnection(conn, true);
        }
        catch(Exception e) {
            threw = true;
        }
        assertTrue("getUser did not throw when given a non-existant usernamme", threw);

        Connection conn = DBConnManager.getConnection();
        User returnedUser = UserDAO.getUser("username");
        DBConnManager.closeConnection(conn, true);
        assertTrue("getUser did not get user", returnedUser.equals(u));
    }

}