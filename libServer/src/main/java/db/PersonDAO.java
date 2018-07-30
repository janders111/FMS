package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Person;
import service.GetName;

/**
 * Class for interfacing or doing anything with the person table in our database.
 */
public class PersonDAO extends DBConnManager {
    /**
     * Gets the person with the given ID. Might overload this function in the future.
     *
     * @param personID ID of the person whose object you want to access
     * @return the found person object, or null if not found.
     */
    public static Person getPerson(String personID, String descendant) throws SQLException {
        Connection conn1 = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Person resultPerson = null;
        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT * FROM Persons WHERE PersonID = ? AND Descendant = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, personID);
            stmt.setString(2, descendant);

            rs = stmt.executeQuery();
            if (rs.next()) {
                resultPerson = new Person(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8));
            } else {
                throw new SQLException("No person found with that ID and descendant.");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(conn1, true);
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return resultPerson;
    }

    /**
     * Get an array of all the people associated with a user
     *
     * @param userName username of user whose people you want
     * @return An array of the people associated with that user
     */
    public static ArrayList<Person> getUsersPeoples(String userName) throws SQLException {
        Connection conn1 = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Person> personArr = new ArrayList<Person>();

        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT * FROM Persons WHERE Descendant = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, userName);

            rs = stmt.executeQuery();
            Boolean found = false;
            while (rs.next()) {
                found = true;
                Person p = new Person(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8));
                personArr.add(p);
            }
            if (!found) {
                throw new SQLException("No family found for that user.");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnection(conn1, true);
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return personArr;
    }


    public static void createPerson(Person p, Connection conn) throws Exception {
        checkConnection(conn);

        if (p.getGender().equals("m") == false) {
            if (p.getGender().equals("f") == false)
                throw new IOException("gender must be m/f");
        }
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Persons (PersonID, Descendant, FirstName, " +
                "LastName, Gender, Mother, Father, Spouse) VALUES(?,?,?,?,?,?,?,?)";

        stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getPersonID());
        stmt.setString(2, p.getDescendant());
        stmt.setString(3, p.getFirstName());
        stmt.setString(4, p.getLastName());
        stmt.setString(5, p.getGender());
        stmt.setString(6, p.getMother());
        stmt.setString(7, p.getFather());
        stmt.setString(8, p.getSpouse());

        if (stmt.executeUpdate() != 1) {
            throw new SQLException("createPerson failed: Could not insert");
        }
        if (stmt != null) {
            stmt.close();
        }
    }


    public static Boolean deleteUsersPeople(String userID, Connection conn) throws SQLException {
        checkConnection(conn);
        Boolean success = true;
        PreparedStatement stmt = null;
        String sql = "DELETE FROM Persons WHERE Descendant = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, userID);
        stmt.executeUpdate();

        if (stmt != null) {
            stmt.close();
        }
        return success;
    }
}
