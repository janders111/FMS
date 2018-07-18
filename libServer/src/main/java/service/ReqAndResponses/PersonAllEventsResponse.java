package service.ReqAndResponses;

import service.Event;

/**
 * Response given by the PersonAllEvents function
 */
public class PersonAllEventsResponse {
    Event eventArray[];
    String ErrMessage;

    /**
     *
     * @param eventArray  Aarray of ALL events for ALL family members of the current user
     * @param errMessage Has error message if error occured, null if no error.
     */
    public PersonAllEventsResponse(Event[] eventArray, String errMessage) {
        this.eventArray = eventArray;
        ErrMessage = errMessage;
    }

    public Event[] getEventArray() {
        return eventArray;
    }

    public void setEventArray(Event[] eventArray) {
        this.eventArray = eventArray;
    }

    public String getErrMessage() {
        return ErrMessage;
    }

    public void setErrMessage(String errMessage) {
        ErrMessage = errMessage;
    }
}
