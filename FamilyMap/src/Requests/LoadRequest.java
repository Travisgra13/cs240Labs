package Requests;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Holds the request body for Load
 */

public class LoadRequest {
    /**
     * the users to be added
     */
    private User[] users;
    /**
     * the people to be added
     */
    private Person[] persons;
    /**
     * the events to be added
     */
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public void setMyUsers(User[] users) {
        this.users = users;
    }

    public void setMyPersons(Person[] persons) {
        this.persons = persons;
    }

    public void setMyEvents(Event[] events) {
        this.events = events;
    }

    public User[] getMyUsers() {
        return users;
    }

    public Person[] getMyPersons() {
        return persons;
    }

    public Event[] getMyEvents() {
        return events;
    }
}
