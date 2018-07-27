package model;

import java.util.Objects;

/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called Persons.
 */
public class Person {
    String personID;
    String descendant;
    String firstName;
    String lastName;
    String gender;
    String mother;
    String father;
    String spouse;

    /**
     *
     * @param personID Person’s unique ID
     * @param descendant  Name of user account this person belongs to
     * @param firstName
     * @param lastName
     * @param gender Person’s gender ("m" or "f")
     * @param mother
     * @param father OPTIONAL, can be missing
     * @param spouse OPTIONAL, can be missing
     */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String mother, String father, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.spouse = spouse;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(descendant, person.descendant) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(mother, person.mother) &&
                Objects.equals(father, person.father) &&
                Objects.equals(spouse, person.spouse);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personID, descendant, firstName, lastName, gender, mother, father, spouse);
    }
}
