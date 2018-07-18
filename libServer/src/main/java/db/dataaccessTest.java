package db;

/**
 * From the class website
 */

import java.sql.*;
import java.util.*;

public class dataaccessTest {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;

    public void openConnection() throws SQLException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:spellcheck.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new SQLException("openConnection failed", e);
        }
    }

    public void closeConnection(boolean commit) throws SQLException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new SQLException("closeConnection failed", e);
        }
    }

    public void createTables() throws SQLException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists dictionary");
                stmt.executeUpdate("create table dictionary ( word text not null unique )");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new SQLException("createTables failed", e);
        }
    }

    public void fillDictionary() throws SQLException {
        try {
            String[] words = {"fred", "wilma", "betty", "barney"};
            PreparedStatement stmt = null;
            try {
                String sql = "insert into dictionary (word) values (?)";
                stmt = conn.prepareStatement(sql);

                for (String word : words) {
                    stmt.setString(1, word);

                    if (stmt.executeUpdate() != 1) {
                        throw new SQLException("fillDictionary failed: Could not insert word");
                    }
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new SQLException("fillDictionary failed", e);
        }
    }

    public Set<String> loadDictionary() throws SQLException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select word from dictionary";
                stmt = conn.prepareStatement(sql);

                Set<String> words = new HashSet<>();
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    words.add(word);
                }

                return words;
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new SQLException("fillDictionary failed", e);
        }
    }


    public static void main(String[] args) {
        try {
            dataaccessTest db = new dataaccessTest();

            db.openConnection();
            db.createTables();
            db.fillDictionary();
            Set<String> words = db.loadDictionary();
            db.closeConnection(true);

            System.out.println("OK");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}