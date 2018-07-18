package service;

import service.ReqAndResponses.AuthTokenRequest;
import service.ReqAndResponses.PersonAllFamilyResponse;
import service.ReqAndResponses.ResponseMessage;

/**
 * Returns ALL family members of the current user. The current user is
 determined from the provided auth token. /person

 */
public class PersonAllFamily {
    /**
     * Returns ALL family members of the current user. The current user is
     determined from the provided auth token.
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    PersonAllFamilyResponse personAllFamily(AuthTokenRequest r) {
        return null;
    }
}
