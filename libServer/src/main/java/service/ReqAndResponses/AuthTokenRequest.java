package service.ReqAndResponses;

/**
 * serves service functions that only need the authToken in the request.
 */
public class AuthTokenRequest {
    String AuthToken;

    /**
     *
     * @param authToken To authorize the user logged in
     */
    public AuthTokenRequest(String authToken) {
        AuthToken = authToken;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }
}
