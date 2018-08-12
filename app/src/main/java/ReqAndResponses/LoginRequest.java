package ReqAndResponses;

/**
 * Holds information needed to request a login for a user.
 */
public class LoginRequest {
    String userName;
    String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
