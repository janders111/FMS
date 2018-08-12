package ReqAndResponses;

/**
 * If login is sucessful, returns info about the person and an auth token. Otherwise, it returns an error message.
 */
public class LoginResponse {
    String userName;
    String authToken;
    String personID;
    String message;

    /**
     *
     * @param userName Same as the one included in the request
     * @param authToken Login Auth token
     * @param personID
     * @param message only filled if an error occured. Otherwise null.
     */
    public LoginResponse(String userName, String authToken, String personID, String message) {
        this.userName = userName;
        this.authToken = authToken;
        this.personID = personID;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean valid() {
        if(userName == null ||
                authToken == null ||
                personID == null) {
            return false;
        }
        return true;
    }
}
