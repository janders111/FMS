package service;

import junit.framework.TestCase;
import java.sql.Connection;
import db.AuthTokenDAO;
import db.DBConnManager;
import db.EventDAO;
import db.UserDAO;
import model.User;
import service.ReqAndResponses.RegisterResponse;
import service.ReqAndResponses.ResponseMessage;

public class RegisterServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
    }

    public void testRegister() throws Exception {
        User u = new User("username", "password",
                                    "e", "j", "a", "m", "id");
        RegisterResponse res = (RegisterResponse)RegisterService.register(u);

        //test that user was added
        assertTrue("User not added", UserDAO.usernameExists("username"));

        //test that fill was called
        Connection conn = DBConnManager.getConnection();
        int numEvents = EventDAO.getNumEvents("username", conn);
        DBConnManager.closeConnection(conn, true);
        assertEquals("Events were not added", numEvents, 91);

        //test that user was logged in and has authToken
        String userName = res.getUserName();
        String returnedName = AuthTokenDAO.getUsernameFromToken(res.getAuthToken());
        assertTrue("authToken not found for added user", userName.equals(returnedName));
    }

    public void testRegisterInvalidUserName() throws Exception {
        Boolean threw = false;
        //user with null username
        User u = new User(null, "password",
                "e", "j", "a", "m", "id");

        ResponseMessage res = (ResponseMessage) RegisterService.register(u);
        assertTrue("Should have returned error for sql constraint violation", res.isError());
    }

    public void testRegisterInvalidUserGender() throws Exception {
        Boolean threw = false;
        //user with null username
        User u = new User("n", "password",
                "e", "j", "a", "r", "id");

        ResponseMessage res = (ResponseMessage) RegisterService.register(u);
        assertTrue("Should have returned error for sql constraint violation", res.isError());
    }
}