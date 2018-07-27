package service;

import java.util.ArrayList;

import db.AuthTokenDAO;
import db.EventDAO;
import model.Event;
import service.ReqAndResponses.EventRequest;
import service.ReqAndResponses.PersonAllEventsResponse;
import service.ReqAndResponses.ResponseMessage;

/**
 * Returns the single Event object with the specified ID. /event/[eventID]
 * Or if no event ID is provided, returns Returns ALL events for ALL family
 * members of the current user.
 */

public class EventService {
    /**
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return responseMessage, or EventResponse object
     */
    public static Object event(EventRequest r) {
        String token = r.getAuthToken();
        String foundUsername;

        try {
            //this will throw if token not found
            foundUsername = AuthTokenDAO.getUsernameFromToken(token);
            //if the input URL does not have the person ID
            if (r.getEventID() == null) {//Return ALL family members of the current user.
                ArrayList<Event> arrayL = EventDAO.getUsersEvents(foundUsername);
                Event[] EventsArr = arrayL.toArray(new Event[arrayL.size()]);
                return new PersonAllEventsResponse(EventsArr, null);
            }
            //if the URL has a person ID
            else {//Return the single event object with the specified ID.
                return EventDAO.getEvent(r.getEventID(), foundUsername);
            }
        }
        catch (Exception e) {
            return new ResponseMessage(e.toString(), true);
        }
    }
}