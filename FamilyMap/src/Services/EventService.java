package Services;

import Model.Event;
import Requests.EventIDRequest;
import Requests.EventRequest;
import Result.EventIDResult;
import Result.EventResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * This service executes all Daos required to do Event
 */
public class EventService {
    private Database db;

    public EventService(Database db) {
        this.db = db;
    }
    /**
     * This gets the events of all family members of a user
     * @param eventRequest the request body
     * @return response body
     */
    public EventResult event(EventRequest eventRequest) throws  DataAccessException {
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            events = eDao.ReturnAllEvents(eventRequest.getUserName());
            db.closeConnection(true);
            return new EventResult(events, true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            return new EventResult(null,false);
        }
    }
    public EventIDResult eventID(EventIDRequest eventIDRequest) throws DataAccessException {
        //IN Handler check for correct authtoken and that it belongs to the user
        Event myEvent = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            myEvent = eDao.QueryEvent(eventIDRequest.getEventID());
            if (myEvent == null) {
                throw new DataAccessException("Invalid EventID Parameter");
            }
            if (!myEvent.getDescendant().equals(eventIDRequest.getUser())) {
                throw new DataAccessException("Not Valid User");
            }
            db.closeConnection(true);
            return new EventIDResult(myEvent, true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            return new EventIDResult(null,false);
        }
    }
}
