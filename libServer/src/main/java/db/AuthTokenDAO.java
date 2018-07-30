package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AuthToken;

/**
 * Class for interfacing or doing anything with the AuthToken table in our database. It might seem
 * like there should be a create authToken function, but this is done at the time the user makes
 * an account. See userDAO.
 */
public class AuthTokenDAO extends DBConnManager {

    public static void createAuthToken(AuthToken TokenObj, Connection conn) throws SQLException{
        checkConnection(conn);
        String sql = "DELETE FROM AuthTokens WHERE Username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, TokenObj.getUserName());
        stmt.executeUpdate();

        sql = "INSERT INTO AuthTokens (Username, Token) VALUES(?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, TokenObj.getUserName());
        stmt.setString(2, TokenObj.getToken());

        if (stmt.executeUpdate() != 1) {
            throw new SQLException("createPerson failed: Could not insert");
        }
        if (stmt != null) {
            stmt.close();
        }
    }

    public static String getUsernameFromToken(String authToken) throws SQLException {
        Connection conn1 = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String userName = null;

        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT Username FROM AuthTokens WHERE Token = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, authToken);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            else {
                throw new SQLException("authToken not found.");
            }
        }
        catch(SQLException e) {
            throw e;
        }
        finally {
            closeConnection(conn1, true);
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }
}

