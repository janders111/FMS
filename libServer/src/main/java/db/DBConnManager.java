package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * All of the DAOs extend this class. Provides functionalities like getting and closing connections,
 * making tables, etc.
 */

public class DBConnManager {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createTables(Connection conn) throws SQLException {
        checkConnection(conn);
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("" +
                "CREATE TABLE IF NOT EXISTS `Events` (\n" +
                "        `EventID`       TEXT NOT NULL UNIQUE,\n" +
                "        `Descendant`    TEXT NOT NULL,\n" +
                "        `PersonID`        TEXT,\n" +
                "        `Latitude`      REAL,\n" +
                "        `Longitude`     REAL,\n" +
                "        `Country`       TEXT,\n" +
                "        `City`  TEXT,\n" +
                "        `EventType`     TEXT,\n" +
                "        `Year`  INTEGER,\n" +
                "        PRIMARY KEY(EventID)\n" +
                ");\n" );
        stmt.executeUpdate("" +
                "CREATE TABLE IF NOT EXISTS `Persons` (\n" +
                "        `PersonID`      TEXT NOT NULL UNIQUE,\n" +
                "        `Descendant`    TEXT NOT NULL,\n" +
                "        `FirstName`     TEXT NOT NULL,\n" +
                "        `LastName`      TEXT NOT NULL,\n" +
                "        `Gender`        TEXT,\n" +
                "        `Mother`        TEXT,\n" +
                "        `Father`        TEXT,\n" +
                "        `Spouse`        TEXT,\n" +
                "        CHECK(Gender in ('m', 'f'))\n" +
                "        PRIMARY KEY(PersonID)\n" +
                ");\n" );
        stmt.executeUpdate("" +
                "CREATE TABLE IF NOT EXISTS `Users` (\n" +
                "        `Username`      TEXT NOT NULL UNIQUE,\n" +
                "        `Password`      TEXT NOT NULL,\n" +
                "        `Email` TEXT NOT NULL,\n" +
                "        `FirstName`     TEXT NOT NULL,\n" +
                "        `LastName`      TEXT NOT NULL,\n" +
                "        `Gender`        TEXT,\n" +
                "        `PersonID`      TEXT NOT NULL,\n" +
                "        CHECK(Gender in ('m', 'f'))\n" +
                "        PRIMARY KEY(Username)\n" +
                ");\n" );
        stmt.executeUpdate("" +
                "CREATE TABLE IF NOT EXISTS `AuthTokens` (\n" +
                "        `Username`      TEXT NOT NULL,\n" +
                "        `Token` TEXT NOT NULL UNIQUE,\n" +
                "        PRIMARY KEY(Token)\n" +
                ");\n" );
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void clearTables(Connection conn) throws SQLException {
        checkConnection(conn);
        Statement stmt = null;
        stmt = conn.createStatement();
        //instead of clearing the tables, it is better to DELETE them and recreate them.
        stmt.executeUpdate("DROP TABLE IF EXISTS `Events`;\n" );
        stmt.executeUpdate("DROP TABLE IF EXISTS `Persons`;\n" );
        stmt.executeUpdate("DROP TABLE IF EXISTS `Users`;\n" );
        stmt.executeUpdate("DROP TABLE IF EXISTS `AuthTokens`;\n" );
        createTables(conn);
        if (stmt != null) {
            stmt.close();
        }
    }

    public static Connection getConnection() throws SQLException{
        Connection conn = null;
        final String CONNECTION_URL = "jdbc:sqlite:fms.sqlite";
        conn = DriverManager.getConnection(CONNECTION_URL);
        // Start a transaction
        conn.setAutoCommit(false);
        return conn;
    }

    public static void closeConnection(Connection conn, boolean commit) throws SQLException{
        if(conn == null) {
            return;
        }
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
            throw new SQLException("closeConnection failed  " + e.toString());
        }
    }

    public static void checkConnection(Connection conn) throws SQLException{
        if(conn == null) {
            throw new SQLException("Connection was null. Did you remember to open connection?");
        }
    }
}
