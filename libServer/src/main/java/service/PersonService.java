package service;

import java.sql.SQLException;
import java.util.ArrayList;

import db.AuthTokenDAO;
import db.DBConnManager;
import db.PersonDAO;
import db.UserDAO;
import model.Person;
import service.ReqAndResponses.PersonAllFamilyResponse;
import service.ReqAndResponses.PersonRequest;
import service.ReqAndResponses.ResponseMessage;


/**
 *  returns a person if given a person ID, or all people associated with the user if not
 *  given a person ID
 *  */
public class PersonService {
    /**
     *  returns a person if given a person ID, or all people associated with the user if not
     *  given a person ID
     *
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    public static Object Person(PersonRequest r) {
        String token = r.getAuthToken();
        String foundUsername;
        //make sure that you return things belonging to the user.

        try {
            //this will throw if token not found
            foundUsername = AuthTokenDAO.getUsernameFromToken(token);
            //if the input URL does not have the person ID
            if (r.getPersonID() == null) {//Return ALL family members of the current user.
                ArrayList<Person> arrayL = PersonDAO.getUsersPeoples(foundUsername);
                Person[] PersonsArr = arrayL.toArray(new Person[arrayL.size()]);
                return new PersonAllFamilyResponse(PersonsArr, null);
            }
            //if the URL has a person ID
            else {//Return the single Person object with the specified ID.
                return PersonDAO.getPerson(r.getPersonID(), foundUsername);
            }
        }
        catch (Exception e) {
            return new ResponseMessage(e.toString(), true);
        }
    }
}
