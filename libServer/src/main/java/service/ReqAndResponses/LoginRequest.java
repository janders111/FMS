package service.ReqAndResponses;

/**
 * Holds information needed to request a login for a user.
 */
public class LoginRequest {
    String Username;
    String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
