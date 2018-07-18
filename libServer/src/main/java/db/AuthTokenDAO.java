package db;

import java.sql.SQLException;
import java.sql.Statement;

import model.AuthToken;
import model.User;

/**
 * Class for interfacing or doing anything with the AuthToken table in our database. It might feel
 * like there should be a create authToken function, but this is done at the time the user makes
 * an account. See userDAO.
 */
public class AuthTokenDAO extends DBConnManager {
    /**
     * To check whether the AuthToken really corresponds to a user.
     * @param u Must contain AuthToken and UserID field.
     * @return True if the AuthToken does correspond to that user.
     */
    public boolean checkAuthToken(User u) {
        return true;
    }

    /**
     * To get a user's auth token.
     * @param u Must contain AuthToken and UserID field.
     * @return The authToken in a wrapper object
     */
    public AuthToken getAuthToken(User u) {
        return null;
    }

    public Boolean deleteAuthToken(String username) {
        return true;
    }
}

