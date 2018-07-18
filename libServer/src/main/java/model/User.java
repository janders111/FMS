package model;
/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called Users.
 */

public class User {
    String Username;
    String Password;
    String Email;
    String FirstName;
    String LastName;
    String Gender;
    String PersonID;

    /**
     *
     * @param username Unique handle for user
     * @param password User's password
     * @param email User's email
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender "m" or "f"
     * @param personID Unique ID generated for person
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        Username = username;
        Password = password;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        PersonID = personID;
    }

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }
}
