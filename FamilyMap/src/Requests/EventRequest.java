package Requests;

import Model.Event;

import java.util.ArrayList;

/**
 * Holds the request body for Event
 */
public class EventRequest {
    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EventRequest(String userName) {
        this.userName = userName;
    }

}
