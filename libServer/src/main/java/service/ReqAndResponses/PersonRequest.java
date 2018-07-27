package service.ReqAndResponses;

/**
 * Object that can hold information needed to make a person request.
 */
public class PersonRequest {
    String PersonID;
    String AuthToken;

    public PersonRequest(String personID, String authToken) {
        PersonID = personID;
        AuthToken = authToken;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }
}
