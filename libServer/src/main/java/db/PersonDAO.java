package db;

import model.Person;

/**
 * Class for interfacing or doing anything with the person table in our database.
 */
public class PersonDAO extends DAO {
    /**
     * Tells if the person passed in already exists.
     * @param p Person ID must be filled in.
     * @return True if person does exist.
     */
    public Boolean personExists(Person p) {
        return true;
    }

    /**
     * Gets the person with the given ID. Might overload this function in the future.
     * @param personID ID of the person whose object you want to access
     * @return the found person object, or null if not found.
     */
    public Person getPerson(String personID) {
        return null;
    }
    public Person[] getUsersPeoples(String userID) {
        return null;
    }
    public Person createPerson(Person p) {
        return null;
    }
    public Boolean deletePerson(String personID) {
        return false;
    }
}
