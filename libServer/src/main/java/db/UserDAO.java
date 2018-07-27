package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 * Class for interfacing or doing anything with the user table in our database.
 */
public class UserDAO extends DBConnManager {
    /**
     * Gets the user object of the user, given a user ID.
     * @param userName userName for the user whose User object you want.
     * @return the found user, or null if not found.
     */
    public static User getUser (String userName) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User result = null;
        Connection conn1 = null;
        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT * FROM Users WHERE Username = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String Username = rs.getString(1);
                String Password = rs.getString(2);
                String Email = rs.getString(3);
                String FirstName = rs.getString(4);
                String LastName = rs.getString(5);
                String Gender = rs.getString(6);
                String PersonID = rs.getString(7);
                result = new User(Username, Password, Email, FirstName, LastName,
                        Gender, PersonID);
            } else {
                throw new SQLException("getUser: No users found with that username.");
            }
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            closeConnection(conn1, true);
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return result;
    }

    public static Boolean usernameExists (String userName) throws SQLException{
        Connection conn1 = DBConnManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean exists;

        try {
            String sql = "SELECT * FROM Users WHERE Username = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                exists = true;
            } else {
                exists = false;
            }
        }
        catch(SQLException e) {
            throw e;
        }
        finally {
            closeConnection(conn1, true);
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return exists;
    }

    public static void createUser(User u, Connection conn) throws Exception{
        checkConnection(conn);
        if(u.getGender().equals("m") == false) {
            if(u.getGender().equals("f") == false)
                throw new IOException("Gender must be m/f");
        }
        if(usernameExists(u.getUserName())) {
            throw new SQLException("Username is not unique");
        }
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Users (Username, Password, Email, " +
                "FirstName, LastName, Gender, PersonID) VALUES(?,?,?,?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonID());
            if (stmt.executeUpdate() != 1) {
                throw new SQLException("createPerson Could not insert");
            }
        }
        catch(SQLException e) {
            throw new SQLException("Check that your request meets the sql constraints.");
        }
        if (stmt != null) {
            stmt.close();
        }
    }
}
