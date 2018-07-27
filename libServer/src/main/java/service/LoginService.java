package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import db.AuthTokenDAO;
import db.DBConnManager;
import db.UserDAO;
import model.AuthToken;
import model.User;
import service.ReqAndResponses.LoginRequest;
import service.ReqAndResponses.LoginResponse;
import service.ReqAndResponses.ResponseMessage;

/** Logs in the user and returns an auth token.*/
public class LoginService {
    /**
     * Attempt to log in the user
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return error message, or loginResponse object
     */
     public static Object login(LoginRequest r) {
         String userName = r.getUsername();
         String password = r.getPassword();
         Connection conn = null;
         User userObj = null;
         LoginResponse response = null;

         try {
             conn = DBConnManager.getConnection();
             try {
                 userObj = UserDAO.getUser(userName);
             } catch (Exception e) {
                 throw new IOException("Unable to find username.");
             }
             if (userObj != null && userObj.getPassword().equals(password)) {
                 try {
                     UUID newUUID = UUID.randomUUID();
                     AuthToken modelAuthToken = new AuthToken(r.getUsername(), newUUID.toString());
                     AuthTokenDAO.createAuthToken(modelAuthToken, conn);
                     return new LoginResponse(userObj.getUserName(), modelAuthToken.getToken(),
                             userObj.getPersonID(), null);
                 }
                 catch(Exception e) {
                     throw new Exception("Server error.");
                 }
             } else {
                 throw new IOException("Wrong password.");
             }
         }
         catch(Exception e) {
             return new ResponseMessage(e.toString(), true);
         }
         finally {
             try{DBConnManager.closeConnection(conn, true);}
             catch(Exception e){e.printStackTrace();}
         }
     }
}
