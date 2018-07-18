package service.ReqAndResponses;

/**
 * If login is sucessful, returns info about the person and an auth token. Otherwise, it returns an error message.
 */
public class LoginResponse {
    String Username;
    String Token;
    String PersonID;
    String Message;

    /**
     *
     * @param username Same as the one included in the request
     * @param token Login Auth token
     * @param personID
     * @param message only filled if an error occured. Otherwise null.
     */
    public LoginResponse(String username, String token, String personID, String message) {
        Username = username;
        Token = token;
        PersonID = personID;
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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
