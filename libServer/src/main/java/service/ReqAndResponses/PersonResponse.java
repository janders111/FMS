package service.ReqAndResponses;

/**
 * Response information from person function.
 */
public class PersonResponse {
    String Descendant;
    String PersonID;
    String FirstName;
    String LastName;
    String Gender;
    String Father;
    String Mother;
    String Spouse;
    String AuthToken;
    String ErrMessage;

    /**
     *
     * @param descendant Name of user account this person belongs to
     * @param personID  Person’s unique ID
     * @param firstName
     * @param lastName
     * @param gender
     * @param father  Person’s gender ("m" or "f")
     * @param mother OPTIONAL, can be missing
     * @param spouse OPTIONAL, can be missing
     * @param authToken OPTIONAL, can be missing
     * @param errMessage message that is only not null when an error occurs. Describes the error.
     */
    public PersonResponse(String descendant, String personID, String firstName, String lastName, String gender, String father, String mother, String spouse, String authToken, String errMessage) {
        Descendant = descendant;
        PersonID = personID;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Father = father;
        Mother = mother;
        Spouse = spouse;
        AuthToken = authToken;
        ErrMessage = errMessage;
    }

    public String getDescendant() {
        return Descendant;
    }

    public void setDescendant(String descendant) {
        Descendant = descendant;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
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

    public String getFather() {
        return Father;
    }

    public void setFather(String father) {
        Father = father;
    }

    public String getMother() {
        return Mother;
    }

    public void setMother(String mother) {
        Mother = mother;
    }

    public String getSpouse() {
        return Spouse;
    }

    public void setSpouse(String spouse) {
        Spouse = spouse;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }
    public String getErrMessage() {
        return ErrMessage;
    }

    public void setErrMessage(String errMessage) {
        ErrMessage = errMessage;
    }
}
