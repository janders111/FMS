package service.ReqAndResponses;

import model.Event;

/**
 * Response given by the PersonAllEvents function
 */
public class PersonAllEventsResponse {
    Event eventArray[];

    /**
     *
     * @param eventArray  Aarray of ALL events for ALL family members of the current user
     * @param errMessage Has error message if error occured, null if no error.
     */
    public PersonAllEventsResponse(Event[] eventArray, String errMessage) {
        this.eventArray = eventArray;
    }

    public Event[] getEventArray() {
        return eventArray;
    }

    public void setEventArray(Event[] eventArray) {
        this.eventArray = eventArray;
    }

}
