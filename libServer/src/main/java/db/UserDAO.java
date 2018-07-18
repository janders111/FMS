package db;

import model.AuthToken;
import model.Person;
import model.User;

/**
 * Class for interfacing or doing anything with the user table in our database.
 */
public class UserDAO {
    /**
     * Tells if user that is passed in exists.
     * @param u user ID or Username must be filled in. (this may change).
     * @return True of user exists.
     */
    public Boolean userExists(User u) {
        return true;
    }
    /**
     * When generating the tree, use this function to add users to a person's tree.
     * @param u User whose tree we are making.
     * @param p Person to be added to the user's tree.
     * @return True if person was added
     */
    public boolean addPersonToUserTree(User u, Person p) {
        return true;
    }

    /**
     * Gets the user object of the user, given a user ID.
     * @param userID ID for the user's object you want.
     * @return the found user, or null if not found.
     */
    public User getUser (String userID) {
        return null;
    }
    public AuthToken createUser(User u) {
        return null;
    }
    public boolean deleteUser(User u) {
        return true;
    }
}
