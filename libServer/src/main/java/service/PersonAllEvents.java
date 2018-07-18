package service;

import service.ReqAndResponses.AuthTokenRequest;
import service.ReqAndResponses.PersonAllEventsResponse;

/**
 *  Returns ALL events for ALL family members of the current user. The current
 user is determined from the provided auth token. /event
 */
public class PersonAllEvents {
    /**
     * Returns ALL events for ALL family members of the current user. The current
     user is determined from the provided auth token. /event
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    AuthTokenRequest event(PersonAllEventsResponse r) {
        return null;
    }
}
