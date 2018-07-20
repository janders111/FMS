package db;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

import model.Person;

import static org.junit.Assert.*;

public class PersonDAOTest {
    PersonDAO myPersonDAO;
    Person p1;
    Person p2;
    Person p3;

    @org.junit.Before
    public void setUp() throws Exception {
        myPersonDAO = new PersonDAO();
        myPersonDAO.openConnection();
        myPersonDAO.createTables(); //creates tables if not already created
        myPersonDAO.deleteAllPersons(); //makes sure the persons table is empty

        p1 = new Person("1234", "adam", "Jordan", "Andersen",
                "M", "Evelyn", "Mark", "NA");
        p2 = new Person("4566", "adam", "Jacob", "Andersen",
                "M", "Evelyn", "Mark", "NA");
        p3 = new Person("7896", "adam", "Kaitlyn", "Andersen",
                "F", "Evelyn", "Mark", "NA");

        assertTrue("Exception thrown while adding p1", myPersonDAO.createPerson(p1));
        assertTrue("Exception thrown while adding p2", myPersonDAO.createPerson(p2));
        assertTrue("Exception thrown while adding p3", myPersonDAO.createPerson(p3));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        myPersonDAO.closeConnection(true);
        myPersonDAO = null;
    }

    @org.junit.Test
    public void createPerson() {
        Person p1Duplicate = new Person("1234", "adam", "Jordan", "Andersen",
                "M", "Evelyn", "Mark", "NA");
        Person personNoID = new Person(null, "adam", "Jordan", "Andersen",
                "M", "Evelyn", "Mark", "NA");
        Person p4 = new Person("456456", "adam", "Jacob", "Andersen",
                "M", "Evelyn", "Mark", "NA");

        Boolean success = myPersonDAO.createPerson(personNoID);
        assertFalse("CreatePerson returned true when creating a person with no person ID. " +
                        "It should have returned false to indicate failure."
                , success);

        success = myPersonDAO.createPerson(p1Duplicate);
        assertFalse("CreatePerson returned true when creating a person with the same person ID. " +
                        "It should have returned false to indicate failure."
                        , success);

        success = myPersonDAO.createPerson(p4);
        assertTrue("CreatePerson returned false when creating a " +
                        "new valid person. It should have returned true.", success);
    }

    @org.junit.Test
    public void getPerson() {
        Person resultPerson = null;
        try {
            resultPerson = myPersonDAO.getPerson("1234");
        }
        catch(SQLException e) {
            e.toString();
            e.printStackTrace();;
        }
        assertTrue(resultPerson.equals(p1));

        try {
            resultPerson = myPersonDAO.getPerson("12345");
        }
        catch(SQLException e) {
            e.toString();
            e.printStackTrace();;
        }
        assertNull(resultPerson);

        try {
            resultPerson = myPersonDAO.getPerson("");
        }
        catch(SQLException e) {
            e.toString();
            e.printStackTrace();;
        }
        assertNull(resultPerson);
    }

    @org.junit.Test
    public void getUsersPeoples() {

    }

    @org.junit.Test
    public void deletePerson() {
    }
}