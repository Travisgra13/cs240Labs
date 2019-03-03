package Services;

import Model.Event;
import Model.Person;
import Model.User;
import Requests.EventIDRequest;
import Requests.EventRequest;
import Requests.PersonIDRequest;
import Result.EventIDResult;
import Result.EventResult;
import Result.PersonIDResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.util.ArrayList;

public class EventServiceTest {
    Database db;
    User bestUser;
    User secondUser;
    Person bestPerson;
    Person secondPerson;
    Person thirdPerson;
    Event bestEvent;
    Event secondEvent;
    Event thirdEvent;
    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("Obama", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
        secondUser = new User("Donald16", "theWall", "potus21@gmail.com", "Donald", "Trump", "m", "bestPrez");
        bestPerson = new Person("travy", "thomas4", "Travis", "Graham", "m", "Craig", "Lakesha", "Conn");
        secondPerson = new Person("cardi", "thomas4", "Cardi", "B", "f", "Lorice", "Johnisha", "Gordon");
        thirdPerson = new Person("sam", "thomas4", "Samantha", "Thomas", "f", "Royce", "Tory", "Kim");
        bestEvent = new Event("happs54", "Obama", "travy", 10595456.02,1943265.58,"U.S.A.", "provo", "baptism", 2003);
        secondEvent = new Event("happs589", "Obama", "cardi", 10595456.85,1943265.98,"U.S.A.", "provo", "birth", 2005);
        thirdEvent = new Event("happenings", "Obama", "sam", 85785.96,74865.28,"Mexico", "Rin", "death", 1720);
        db.createTables();
    }
    @After
    public void tearDown() throws DataAccessException {
        db.clearTables();
    }
    @Test
    public void eventTestPass() throws DataAccessException {
        ArrayList <Event> myEvents = new ArrayList<>();
        myEvents.add(bestEvent);
        myEvents.add(secondEvent);
        myEvents.add(thirdEvent);
        EventRequest eventRequest = new EventRequest(bestUser.getUserName());
        EventResult expectedEventResult = new EventResult(myEvents, true);
        EventResult actualEventResult = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            eDao.AddEvent(thirdEvent);
            db.closeConnection(true);
            db.openConnection();
            EventService eventService = new EventService(db);
            actualEventResult = eventService.event(eventRequest);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw new DataAccessException("Problem in loadServiceFunc");
        }
        assertTrue(expectedEventResult.getEvents().equals(actualEventResult.getEvents()));
        assertTrue(actualEventResult.isSuccessful());
    }
    @Test
    public void eventTestFail() throws DataAccessException {
        ArrayList <Event> myEvents = new ArrayList<>();
        myEvents.add(bestEvent);
        myEvents.add(secondEvent);
        myEvents.add(thirdEvent);
        EventRequest eventRequest = new EventRequest(secondUser.getUserName());
        EventResult expectedEventResult = new EventResult(myEvents, true);
        EventResult actualEventResult = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            eDao.AddEvent(thirdEvent);
            db.closeConnection(true);
            db.openConnection();
            EventService eventService = new EventService(db);
            actualEventResult = eventService.event(eventRequest);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw new DataAccessException("Problem in loadServiceFunc");
        }
        assertFalse(expectedEventResult.getEvents().equals(actualEventResult.getEvents()));
        assertNull(actualEventResult.getEvents());
        assertTrue(actualEventResult.isSuccessful());
    }
    @Test
    public void EventIDTestPass() throws DataAccessException {
        db.clearTables();
        EventIDRequest eventIDRequest = new EventIDRequest(bestEvent.getEventID(), bestUser.getUserName());
        EventIDResult expectedResult = new EventIDResult(bestEvent, true);
        EventIDResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            db.closeConnection(true);
            db.openConnection();
            EventService eventService = new EventService(db);
            acutalResult = eventService.eventID(eventIDRequest);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(acutalResult.isSuccessful());
        assertTrue(expectedResult.getMyEvent().equals(acutalResult.getMyEvent()));
    }
    @Test
    public void EventIDTestFail() throws DataAccessException {
        db.clearTables();
        EventIDRequest eventIDRequest = new EventIDRequest(bestEvent.getEventID(), secondUser.getUserName());
        EventIDResult expectedResult = new EventIDResult(null, false);
        EventIDResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(thirdEvent);
            eDao.AddEvent(secondEvent);
            db.closeConnection(true);
            db.openConnection();
            EventService eventService = new EventService(db);
            acutalResult = eventService.eventID(eventIDRequest);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertFalse(acutalResult.isSuccessful());
        assertNull(acutalResult.getMyEvent());
    }

}
