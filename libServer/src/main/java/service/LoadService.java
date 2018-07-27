package service;

import java.sql.Connection;

import db.DBConnManager;
import db.EventDAO;
import db.PersonDAO;
import db.UserDAO;
import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.LoadRequest;
import service.ReqAndResponses.ResponseMessage;

/**:  Clears all data from the database (just like the /clear API), and then loads the
 posted user, person, and event data into the database.*/
public class LoadService {
    /**
     * Clears all data from the database, and then loads the
     posted user, person, and event data into the database
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    public static ResponseMessage load(LoadRequest r) {
        int numUsers = 0;
        int numPersons = 0;
        int numEvents = 0;
        Connection conn = null;

        try {
            conn = DBConnManager.getConnection();
            DBConnManager.clearTables(conn);
            DBConnManager.closeConnection(conn, true);
            conn = DBConnManager.getConnection();

            if (r.getUsersArray() != null) {
                numUsers = r.getUsersArray().length;
                for (int i = 0; i < numUsers; i++) {
                    UserDAO.createUser(r.getUsersArray()[i], conn);
                }
            }
            if (r.getPersonsArray() != null) {
                numPersons = r.getPersonsArray().length;
                for (int i = 0; i < numPersons; i++) {
                    PersonDAO.createPerson(r.getPersonsArray()[i], conn);
                }
            }
            if (r.getEventsArray() != null) {
                numEvents = r.getEventsArray().length;
                for (int i = 0; i < numEvents; i++) {
                    EventDAO.createEvent(r.getEventsArray()[i], conn);
                }
            }
            return new ResponseMessage("Successfully added " + numUsers + " users, " + numPersons +
                    " persons, and " + numEvents + " events to the database.",false);
        }
        catch (Exception e) {
            return new ResponseMessage(e.toString(), true);
        }
        finally {
            try{DBConnManager.closeConnection(conn, true);}
            catch(Exception e){e.printStackTrace();}
        }
    }
}
