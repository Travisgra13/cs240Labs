package Requests;

/**
 * Holds the request body for Event
 */
public class EventIDRequest {
    /**
     * Event’s unique ID (non-empty string)
     */
    private String eventID;

    private String user;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public EventIDRequest(String eventID, String user) {

        this.eventID = eventID;
        this.user = user;
    }
}
