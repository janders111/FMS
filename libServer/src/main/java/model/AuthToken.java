package model;

/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called AuthTokens.
 */
public class AuthToken {
    String Username;
    String Token;

    /**
     *
     * @param username unique username for user.
     * @param token token used for login authorization.
     */
    public AuthToken(String username, String token) {
        Username = username;
        Token = token;
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
}
