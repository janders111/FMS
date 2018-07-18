package model;
/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called Persons.
 */
public class Person {
    String PersonID;
    String Descendant;
    String FirstName;
    String LastName;
    String Gender;
    String Mother;
    String Father;
    String Spouse;

    /**
     *
     * @param personID Person’s unique ID
     * @param descendant  Name of user account this person belongs to
     * @param firstName
     * @param lastName
     * @param gender
     * @param father  Person’s gender ("m" or "f")
     * @param mother OPTIONAL, can be missing
     * @param spouse OPTIONAL, can be missing
     */

    public Person(String personID, String descendant, String firstName, String lastName, String gender, String mother, String father, String spouse) {
        PersonID = personID;
        Descendant = descendant;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Mother = mother;
        Father = father;
        Spouse = spouse;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public String getDescendant() {
        return Descendant;
    }

    public void setDescendant(String descendant) {
        Descendant = descendant;
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

    public String getMother() {
        return Mother;
    }

    public void setMother(String mother) {
        Mother = mother;
    }

    public String getFather() {
        return Father;
    }

    public void setFather(String father) {
        Father = father;
    }

    public String getSpouse() {
        return Spouse;
    }

    public void setSpouse(String spouse) {
        Spouse = spouse;
    }
}
