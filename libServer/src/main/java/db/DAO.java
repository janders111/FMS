package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by janders1 on 7/18/18.
 */

public class DAO {
    protected Connection conn;

    public void openConnection() throws SQLException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:fms.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new SQLException("openConnection failed", e);
        }
    }
}
