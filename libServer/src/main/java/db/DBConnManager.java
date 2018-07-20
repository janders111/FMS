package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by janders1 on 7/18/18.
 */

public class DBConnManager {
    public Connection conn;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement stmt = null;
            try {
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
                        //"        CONSTRAINT CHK_Person CHECK (Gender in (M, F)),\n" +
                        "        PRIMARY KEY(Username)\n" +
                        ");\n" );
                stmt.executeUpdate("" +
                        "CREATE TABLE IF NOT EXISTS `AuthTokens` (\n" +
                        "        `Username`      TEXT NOT NULL,\n" +
                        "        `Token` TEXT NOT NULL UNIQUE,\n" +
                        "        PRIMARY KEY(Token)\n" +
                        ");\n" );
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            System.out.println("createTables failed\n" + e.toString());
        }
    }

    public void openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:fms.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("openConnection failed\n" + e.toString());
        }
    }

    public void closeConnection(boolean commit) {
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
            System.out.println("closeConnection failed\n" + e.toString());
        }
    }
}
