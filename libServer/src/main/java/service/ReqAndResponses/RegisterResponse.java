package service.ReqAndResponses;

/**
 * Response after creating a new user, contains data about new user, or an error message.
 */
public class RegisterResponse {
    /**
     * non-empty string, same as passed in username.
     */
    String Username;
    /**
     * non-empty auth token string.
     */
    String Token;
    /**
     * non-empty string, newly generated for the user.
     */
    String PersonID;
    /**
     * Only filled if there is an error. Otherwise this is null.
     */
    String message;

    /**
     *
     * @param username non-empty string, same as passed in username.
     * @param token non-empty auth token string.
     * @param personID non-empty string, newly generated for the user.
     * @param message Only filled if there is an error. Otherwise this is null.
     */
    public RegisterResponse(String username, String token, String personID, String message) {
        Username = username;
        Token = token;
        PersonID = personID;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

}
