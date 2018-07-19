package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Person;

/**
 * Class for interfacing or doing anything with the person table in our database.
 */
public class PersonDAO extends DBConnManager {
    private Connection conn;
    /**
     * Gets the person with the given ID. Might overload this function in the future.
     * @param personID ID of the person whose object you want to access
     * @return the found person object, or null if not found.
     */
    public Person getPerson(String personID) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Person resultPerson;
        try {
            String sql = "SELECT * FROM Persons WHERE PersonID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, personID);

            rs = stmt.executeQuery();
            if(rs == null) {
                return null;
            }
            resultPerson = new Person(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                    rs.getString(8));
        }
        catch (SQLException e) {
            throw new SQLException("getUsersPeoples failed", e);
        }
        finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return resultPerson;
    }


    /**
     * Get an array of all the people associated with a user
     * @param userName username of user whose people you want
     * @return An array of the people associated with that user
     */
    public ArrayList<Person> getUsersPeoples(String userName) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Person> personArr = new ArrayList<Person>();
        try {
            String sql = "SELECT * FROM Persons WHERE Descendant = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Person p = new Person(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8));
                personArr.add(p);
            }
        }
        catch (SQLException e) {
            throw new SQLException("getUsersPeoples failed", e);
        }
        finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return personArr;
    }


    public void createPerson(Person p) throws SQLException{
        PreparedStatement stmt = null;
        try {
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
        }
        catch (SQLException e) {
            throw new SQLException("createPerson failed", e);
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    public void deletePerson(String personID) throws SQLException{
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Persons WHERE PersonID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, personID);

            if (stmt.executeUpdate() != 1) {
                throw new SQLException("deletePerson failed: Could not delete");
            }
        }
        catch (SQLException e) {
            throw new SQLException("deletePerson failed", e);
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
