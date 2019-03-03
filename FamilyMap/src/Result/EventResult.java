package Result;

import Model.Event;

import java.util.ArrayList;
/**
 * Holds the response body for Event
 */
public class EventResult {
    /**
     * all of the events
     */
    private ArrayList<Event> events;
    /**
     * whether or not the response was successful
     */
    private boolean successful;
    private String message;

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public EventResult(ArrayList<Event> events, boolean successful) {
        this.events = events;
        this.successful = successful;
        setMessage();
    }
    /**
     * This returns the message associated with the result
     * @return
     */
    public void setMessage() {
        if (successful) {
            message = "Valid Auth Token";
        }
        else {
            message = "Error";
        }

    }
}
