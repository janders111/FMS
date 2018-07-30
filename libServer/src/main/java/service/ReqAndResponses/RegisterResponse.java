package service.ReqAndResponses;

/**
 * Response after creating a new user, contains data about new user, or an error message.
 */
public class RegisterResponse {
    /**
     * non-empty string, same as passed in username.
     */
    String userName;
    /**
     * non-empty auth token string.
     */
    String authToken;
    /**
     * non-empty string, newly generated for the user.
     */
    String personID;
    /**
     * Only filled if there is an error. Otherwise this is null.
     */
    String message;

    /**
     *
     * @param userName non-empty string, same as passed in username.
     * @param authToken non-empty auth token string.
     * @param personID non-empty string, newly generated for the user.
     * @param message Only filled if there is an error. Otherwise this is null.
     */
    public RegisterResponse(String userName, String authToken, String personID, String message) {
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
}
