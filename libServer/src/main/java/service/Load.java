package service;

import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.LoadRequest;
import service.ReqAndResponses.ResponseMessage;

/**:  Clears all data from the database (just like the /clear API), and then loads the
 posted user, person, and event data into the database.*/
public class Load {
    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     posted user, person, and event data into the database
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    ResponseMessage load(LoadRequest r) {
        return null;
    }
}
