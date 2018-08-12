package ReqAndResponses;

import model.Person;

/**
 * Request for PersonAllFamily function
 */
public class PersonAllFamilyResponse {
    Person personArray[];
    String ErrMessage;

    /**
     *
     * @param personArray Array of all family members of the current user.
     * @param ErrMessage Information about error. If there is no error, this should be null.
     */
    public PersonAllFamilyResponse(Person[] personArray, String ErrMessage) {
        this.personArray = personArray;
        this.ErrMessage = ErrMessage;
    }

    public Person[] getPersonArray() {
        return personArray;
    }

    public void setPersonArray(Person[] personArray) {
        this.personArray = personArray;
    }

    public String getErrMessage() {
        return ErrMessage;
    }

    public void setErrMessage(String ErrMessage) {
        this.ErrMessage = ErrMessage;
    }
}
