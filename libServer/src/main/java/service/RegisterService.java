package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import db.AuthTokenDAO;
import db.DBConnManager;
import db.EventDAO;
import db.PersonDAO;
import db.UserDAO;
import handlers.ObjEncoderDecoder;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import service.ReqAndResponses.RegisterResponse;
import service.ReqAndResponses.ResponseMessage;

/**
 * Creates a new user account
 * */
public class RegisterService {
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth token.
     *
     * @param u See documentation of this object in ReqAndResponses package for more info.
     * @return response message, or registerResponse object
     * @throws Exception
     */
    public static Object register(User u) {
        int initialNumGenerations = 4;
        Connection conn = null;
        AuthToken TokenObj = null;

        try {
            AuthToken newToken;
            conn = DBConnManager.getConnection();

            UUID personID = UUID.randomUUID();
            try {//make new account
                u.setPersonID(personID.toString());
                UserDAO.createUser(u, conn);
            } catch(Exception e) {
                throw new Exception("Failed to create new account.  " + e.toString());
            }

            try{//login (aka make token)
            UUID token = UUID.randomUUID();
            TokenObj = new AuthToken(u.getUserName(), token.toString());
            AuthTokenDAO.createAuthToken(TokenObj, conn);
            } catch(Exception e) {
                throw new Exception("Failed to login.");
            }

            Person p;
            try{//store the user as a person
            UUID momUUID = UUID.randomUUID();
            UUID dadUUID = UUID.randomUUID();
            p = ObjEncoderDecoder.createPersonFromUser(u, momUUID.toString(), dadUUID.toString(), conn);
            } catch(Exception e) {
                throw new Exception("Failed to create user's person information.");
            }

            try {//generate tree and events
                FillService.addBirth(p, 1996, conn); // the user needs a bday.
                FillService.generateTree(p, initialNumGenerations, 1996, conn);
            } catch(Exception e) {
                throw new Exception("Failed to create tree data for user.");
            }
            return new RegisterResponse(TokenObj.getUsername(), TokenObj.getToken(),
                                        personID.toString(), null);
        } catch(Exception e){
            return new ResponseMessage(e.toString(), true);
        } finally {
            try{DBConnManager.closeConnection(conn, true);}
            catch(Exception e){e.printStackTrace();}
        }
    }
}
