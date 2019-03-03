package dataAccess;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * This class is full of methods key to manipulating the sql database and
 * the model objects of the type Event
 */
public class EventDao {
    private Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }
    /**
     * This adds an event into the SQL database
     * @param myEvent the token to be added
     * @return true or false based on whether it failed or not
     */
    public boolean AddEvent(Event myEvent) throws DataAccessException {
        boolean commit = true;
        String sql = "INSERT INTO Event (EventID, Descendant, Person, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myEvent.getEventID());
            stmt.setString(2, myEvent.getDescendant());
            stmt.setString(3, myEvent.getPerson());
            stmt.setDouble(4, myEvent.getLatitude());
            stmt.setDouble(5, myEvent.getLongitude());
            stmt.setString(6, myEvent.getCountry());
            stmt.setString(7, myEvent.getCity());
            stmt.setString(8, myEvent.getEventType());
            stmt.setInt(9, myEvent.getYear());

            stmt.executeUpdate();
        }catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting event into the database");
        }
        return commit;
    }
    /**
     * This finds an Event object and returns the object
     * @param myEventID the token to be queried
     * @return the Event object found, if not found null is returned
     */
    public Event QueryEvent(String myEventID) throws DataAccessException {
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myEventID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                event = new Event(rs. getString("EventID"), rs.getString("Descendant"),
                        rs.getString("Person"), rs.getDouble("Latitude"), rs.getDouble("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        }
        return null;
    }
    /**
     * Deletes an event from the database
     * @param myEventID the token to be deleted
     * @return true or false based on whether it is successful or not
     */
    public boolean DeleteEvent(String myEventID) throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Event WHERE EventID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (QueryEvent(myEventID) == null) {
                throw new SQLException();
            }
            stmt.setString(1, myEventID);
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting Event" + myEventID);
        }
        //Search for unique userName and delete, return true if found, if not return false
        return commit;
    }
    /**
     * Clears the database and Model objects that are of the type Event
     * @return true or false based on whether it is successful or not
     */
    public boolean ClearTableEvents() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }catch (SQLException e) {
            commit = false;
            throw new DataAccessException();
        }
        //go through all entries in sql database and clear delete all
        return commit;
    }

    /**
     * This returns all events from all family members of the user
     * @param userName the userName to which the family members' events will be searched for
     * @return an ArrayList of Event objects that contain all events
     */
    public ArrayList<Event> ReturnAllEvents(String userName) throws DataAccessException{
        Event currEvent = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE Descendant = ?;";
        ArrayList<Event> allEvents = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                currEvent = new Event(rs.getString("eventID"), rs.getString("descendant"),
                        rs.getString("person"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                allEvents.add(currEvent);
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error encountered while finding user");

        }
        if (allEvents.size() == 0) {
            return null;
        }
        //Check if unique userName is found and return the user if found
        return allEvents;
        //run userDao.returnallfamilymembers function to get family members and get their events
    }
}
