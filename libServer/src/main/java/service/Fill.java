package service;

import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.ResponseMessage;

public class Fill {
    /**
     * Populates the server's database with generated data for the specified user name.
     The required "username" parameter must be a user already registered with the server. If there
     is any data in the database already associated with the given user name, it is deleted. The
     optional "generations" parameter lets the caller specify the number of generations of ancestors
     to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     persons each with associated events).
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    
    ResponseMessage fill(FillRequest r) {
        return null;
    }
}
