package service;

import junit.framework.TestCase;
import java.sql.Connection;
import db.AuthTokenDAO;
import db.DBConnManager;
import model.User;
import service.ReqAndResponses.LoginRequest;
import service.ReqAndResponses.LoginResponse;
import service.ReqAndResponses.ResponseMessage;

public class LoginServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        LoginRequest req = new LoginRequest("username", "password");
        User u = new User("username", "password",
                "e", "j", "a", "m", "id");
        Object res = RegisterService.register(u);
    }

    public void testLoginValid() throws Exception{
        LoginRequest req = new LoginRequest("username", "password");
        LoginResponse res = (LoginResponse)LoginService.login(req);

        String tokenFirst = res.getAuthToken();
        String AuthTokenReturnedUsername = AuthTokenDAO.getUsernameFromToken(tokenFirst);
        assertTrue("Login did not store a new authToken", AuthTokenReturnedUsername.equals("username"));

        //run it again, test if new authtoken works and is unique
        res = (LoginResponse)LoginService.login(req);

        String tokenSecond = res.getAuthToken();
        AuthTokenReturnedUsername = AuthTokenDAO.getUsernameFromToken(tokenSecond);
        assertFalse("Login did not store a new " +
                "authToken when called a second time", tokenSecond.equals(tokenFirst));
    }

    public void testLoginWrongPassword() throws Exception{
        Boolean threw = false;
        LoginRequest req = new LoginRequest("username", "asdf");
        ResponseMessage res = (ResponseMessage)LoginService.login(req);
        assertTrue("Login reported success when logging in with wrong password", res.isError());
    }

    public void testLoginInvalidInput() throws Exception{
        Boolean threw = false;
        LoginRequest req = new LoginRequest(null, "password");
        ResponseMessage res = (ResponseMessage)LoginService.login(req);
        assertTrue("Login reported success when logging in with null username", res.isError());
    }
}