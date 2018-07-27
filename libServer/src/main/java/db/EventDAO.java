package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import model.Event;
import model.Person;
import service.GetLocation;

/**
 * Class for interfacing with the event table in our database.
 */
public class EventDAO extends DBConnManager {

    /**
     * Get the event object, given the event ID
     * @param eventID ID of the event object you want
     * @return the found event object, or null if not found.
     */
    public static Event getEvent(String eventID, String descendant) throws Exception {
        Connection conn1 = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Event resultEvent = null;
        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT * FROM Events WHERE EventID = ? AND Descendant = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, eventID);
            stmt.setString(2, descendant);

            rs = stmt.executeQuery();
            if (rs.next()) {
                resultEvent = new Event(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getFloat(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getInt(9));
            } else {
                throw new SQLException("No event found with that ID and descendant.");
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
        return resultEvent;
    }

    public static ArrayList<Event> getUsersEvents(String userName) throws SQLException {
        Connection conn1 = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Event resultEvent;
        ArrayList<Event> eventArr = new ArrayList<Event>();

        try {
            conn1 = DBConnManager.getConnection();
            String sql = "SELECT * FROM Events WHERE Descendant = ?";
            stmt = conn1.prepareStatement(sql);
            stmt.setString(1, userName);

            rs = stmt.executeQuery();
            Boolean found = false;
            while (rs.next()) {
                found = true;
                resultEvent = new Event(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getFloat(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getInt(9));
                eventArr.add(resultEvent);
            }
            if (!found) {
                throw new SQLException("No events found for that user.");
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
        return eventArr;
    }

    public static void deleteUsersEvents(String userName, Connection conn) throws SQLException{
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Events WHERE Descendant = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);

            //if (stmt.executeUpdate() != 1) {
            //    throw new SQLException("deleteUsersEvents failed: Could not delete");
            //}
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static Event createEvent(Event e, Connection conn) throws SQLException {
        checkConnection(conn);
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Events (EventID, Descendant, PersonID, " +
                "Latitude, Longitude, Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";

        stmt = conn.prepareStatement(sql);
        stmt.setString(1, e.getEventID());
        stmt.setString(2, e.getDescendant());
        stmt.setString(3, e.getPersonID());
        stmt.setString(4, String.valueOf(e.getLatitude()));
        stmt.setString(5, String.valueOf(e.getLongitude()));
        stmt.setString(6, e.getCountry());
        stmt.setString(7, e.getCity());
        stmt.setString(8, e.getEventType());
        stmt.setString(9, Integer.toString(e.getYear()));

        if (stmt.executeUpdate() != 1) {
            throw new SQLException("createEvent failed: Could not insert");
        }
        if (stmt != null) {
            stmt.close();
        }
        return null;
    }

    public static void deleteAllEvents(Connection conn) throws SQLException {
        checkConnection(conn);
        PreparedStatement stmt = null;
        String sql = "DELETE FROM Events";
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        if (stmt != null) {
            stmt.close();
        }
    }

    public static int getNumEvents(String Descendant, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numEvents = 0;
        try {
            String sql = "SELECT COUNT( * ) as \"numEvents\"\n" +
                    "    FROM Events\n" +
                    "    WHERE Descendant = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Descendant);
            rs = stmt.executeQuery();
            if (rs.next()) {
                numEvents = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            throw e;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return numEvents;
    }
}
