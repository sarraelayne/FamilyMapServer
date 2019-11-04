package DAO;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;

/**
 * communicates with Event Table in database
 */
public class EventDAO {
    private final Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    public Event readEvent(String eventID) throws DatabaseAccessException {
        try {
            String sql = "select * from Event where eventID = '" + eventID + "'";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Event temp = null;
            while (rs.next())
            {
                temp = new Event("", "", "", 0.0, 0.0, "", "", "", 0);
                temp.setEventID(rs.getString("eventID"));
                temp.setUserName(rs.getString("associatedUsername"));
                temp.setPID(rs.getString("personID"));
                temp.setLat(rs.getDouble("latitude"));
                temp.setLong(rs.getDouble("longitude"));
                temp.setCountry(rs.getString("country"));
                temp.setCity(rs.getString("city"));
                temp.setEventType(rs.getString("eventType"));
                temp.setYear(rs.getInt("year"));
            }

            stmt.close();
            rs.close();

            return temp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DatabaseAccessException("Event read failed");
        }
    }
    public Event find(String eventID) throws DatabaseAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"), rs.getString("personID"),
                        rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getString("country"), rs.getString("city"),
                        rs.getString("eventType"), rs.getInt("year"));
                return event;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error in finding event in database");
        }
        return null;
    }
    public ArrayList<Event> findAll(String username) throws DatabaseAccessException {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"), rs.getString("personID"),
                        rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getString("country"), rs.getString("city"),
                        rs.getString("eventType"), rs.getInt("year"));
                events.add(event);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error while finding all events");
        }
        return events;
    }
    public void insert(Event event) throws DatabaseAccessException {
        String sql = "INSERT INTO Event (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year)"
                + " VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUserName());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLat());
            stmt.setDouble(5, event.getLong());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("Error encountered while adding Event to database");
        }
    }
    public void removeEvent(String eventID) throws DatabaseAccessException {
        String sql = "DELETE FROM Event WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("removeEvent failed", e);
        }
    }
}
