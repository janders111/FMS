package model;

/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called AuthTokens.
 */
public class AuthToken {
    String userName;
    String token;

    /**
     *
     * @param userName unique username for user.
     * @param token token used for login authorization.
     */

    public AuthToken(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
