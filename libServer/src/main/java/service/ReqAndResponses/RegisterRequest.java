package service.ReqAndResponses;

/**
 * Info to create new user. All fields must be filled.
 */
public class RegisterRequest {
    /**
     * non-empty string
     */
    String Username;
    /**
     * non-empty string
     */
    String Password;
    /**
     * non-empty string
     */
    String Email;
    /**
     * non-empty string
     */
    String FirstName;
    /**
     * non-empty string
     */
    String LastName;
    /**
     * non-empty string
     */
    String Gender;

    /**
     *
     * @param username non-empty string
     * @param password non-empty string
     * @param email non-empty string
     * @param firstName non-empty string
     * @param lastName non-empty string non-empty string
     * @param gender "m" or "f"
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        Username = username;
        Password = password;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
    }

    /**
     * "m" or "f"
     */

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
}
