package ReqAndResponses;

/**
 * Object that can hold information needed to make a event request.
 */
public class EventRequest {
    String EventID;
    String AuthToken;

    /**
     *
     * @param eventID unique ID for event
     * @param authToken Check authorization of login
     */
    public EventRequest(String eventID, String authToken) {
        EventID = eventID;
        AuthToken = authToken;
    }

    public String getEventID() {
        return EventID;
    }

    public String getAuthToken() {
        return AuthToken;
    }
}
