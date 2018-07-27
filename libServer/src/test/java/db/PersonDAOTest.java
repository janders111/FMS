package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Person;

import static org.junit.Assert.*;

public class PersonDAOTest {
    Connection conn;
    Person p1;
    Person p2;
    Person p3;

    @org.junit.Before
    public void setUp() throws Exception {
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);

        p1 = new Person("1234", "adam", "Jordan", "Andersen",
                "m", "Evelyn", "Mark", "NA");
        p2 = new Person("4566", "adam", "Jacob", "Andersen",
                "m", "Evelyn", "Mark", "NA");
        p3 = new Person("7896", "adam", "Kaitlyn", "Andersen",
                "f", "Evelyn", "Mark", "NA");

        PersonDAO.createPerson(p1, conn);
        PersonDAO.createPerson(p2, conn);
        PersonDAO.createPerson(p3, conn);
        DBConnManager.closeConnection(conn, true);
        conn = DBConnManager.getConnection();
    }

    @org.junit.Test
    public void testCreatePerson() {
        Boolean threw = false;
        Person p1Duplicate = new Person("1234", "adam", "Jordan", "Andersen",
                "m", "Evelyn", "Mark", "NA");
        Person personNoID = new Person(null, "adam", "Jordan", "Andersen",
                "f", "Evelyn", "Mark", "NA");
        Person badGender = new Person("456456", "adam", "Jacob", "Andersen",
                "ooo", "Evelyn", "Mark", "NA");

        try {
            PersonDAO.createPerson(personNoID, conn);
        } catch(Exception e) {
            threw = true;
        }
        assertTrue("CreatePerson did not throw when creating a person with no person ID. ", threw);
        threw = false;
        try {
            PersonDAO.createPerson(p1Duplicate, conn);
        } catch(Exception e)  {
            threw = true;
        }
        assertTrue("CreatePerson did not throw when creating a person with the same person ID. ", threw);

        try {
            PersonDAO.createPerson(badGender, conn);
        } catch(Exception e) {
            threw = true;
        }
        assertTrue("CreatePerson did not throw when creating a person without a valid gender. ", threw);
    }

    @org.junit.Test
    public void testGetPersonException() throws Exception{
        Person resultPerson = null;
        Boolean threw = false;

        try {
            resultPerson = PersonDAO.getPerson("12345");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getPerson did not throw when it was given a non-existant personID", threw);

        threw = false;
        try {
            resultPerson = PersonDAO.getPerson(null);
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getPerson did not throw when it was given a null personID", threw);
    }

    @org.junit.Test
    public void testGetPerson() throws Exception{
        Person result = PersonDAO.getPerson("1234");
        assertTrue("getPerson did not get right person e1", p1.equals(result));
        result = PersonDAO.getPerson("4566");
        assertTrue("getPerson did not get right person e2", p2.equals(result));
        result = PersonDAO.getPerson("7896");
        assertTrue("getPerson did not get right person e3", p3.equals(result));
    }

    @org.junit.Test
    public void testGetUsersPeoples() throws Exception{
        ArrayList<Person> pArr = PersonDAO.getUsersPeoples("adam");
        assertEquals("GetUsersPeople did not return the right number of " +
                            "people (with username adam)", pArr.size(), 3);
        Boolean threw = false;
        try {
            PersonDAO.getUsersPeoples("asdf");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getPerson did not throw when it was given a username with no family.", threw);
    }

    @org.junit.Test
    public void testDeletePerson() throws Exception{
        Connection conn = DBConnManager.getConnection();
        PersonDAO.deleteUsersPeople("adam", conn);
        DBConnManager.closeConnection(conn, true);
        Boolean threw = false;
        try {
            PersonDAO.getUsersPeoples("adam");
        }
        catch(SQLException e) {
            threw = true;
        }
        assertTrue("getPerson did not throw when querying deleted people", threw);
    }
}