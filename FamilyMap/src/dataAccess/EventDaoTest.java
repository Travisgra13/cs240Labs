package dataAccess;

import Model.Event;
import Model.Person;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class EventDaoTest {
    Database db;
    User bestUser;
    Person bestPerson;
    Person secondPerson;
    Person thirdPerson;
    Event bestEvent;
    Event secondEvent;
    Event thirdEvent;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("thomas4", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
        bestPerson = new Person("travy", "thomas4", "Travis", "Graham", "m", "Craig", "Lakesha", "Conn");
        secondPerson = new Person("cardi", "thomas4", "Cardi", "B", "f", "Lorice", "Johnisha", "Gordon");
        thirdPerson = new Person("sam", "thomas4", "Samantha", "Thomas", "f", "Royce", "Tory", "Kim");
        bestEvent = new Event("happs54", "thomas4", "travy", 10595456.02,1943265.58,"U.S.A.", "provo", "baptism", 2003);
        secondEvent = new Event("happs589", "johnny3", "cardi", 10595456.85,1943265.98,"U.S.A.", "provo", "birth", 2005);
        thirdEvent = new Event("happenings", "thomas4", "sam", 85785.96,74865.28,"Mexico", "Rin", "death", 1720);
        db.createTables();
    }
    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }
    @Test
    public void insertPass() throws Exception {
        Event compareEvent = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            compareEvent = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareEvent);
        assertEquals(bestEvent, compareEvent);
    }
    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(bestEvent);
            db.closeConnection(didItWork);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Event compareEvent = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            compareEvent = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareEvent);
    }
    @Test
    public void testQueryEvent() throws DataAccessException{
        Event compareEvent = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            compareEvent = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(compareEvent);
        assertEquals(bestEvent, compareEvent);
    }
    @Test
    public void testFailQueryEvent() throws DataAccessException {
        boolean didItWork = true;
        Event compareTest = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            compareTest = eDao.QueryEvent(thirdEvent.getEventID());
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertNull(compareTest);
    }
    @Test
    public void testDeleteEventPass() throws DataAccessException{
        Event compareEvent = null;
        boolean didItWork = true;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            eDao.AddEvent(thirdEvent);
            eDao.DeleteEvent(bestEvent.getEventID());
            compareEvent = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertNull(compareEvent);
        assertTrue(didItWork);
    }
    @Test
    public void testDeleteFail() throws DataAccessException {
        boolean didItWork = true;
        Event compareTest = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            compareTest = eDao.QueryEvent(thirdEvent.getEventID());
            didItWork = eDao.DeleteEvent(thirdEvent.getEventID());
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertFalse(didItWork);
        assertNull(compareTest);
    }
    @Test
    public void testClearAllPass() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        Event bestEventCheck = null;
        Event secondEventCheck = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            eDao.ClearTableEvents();
            bestEventCheck = eDao.QueryEvent(bestEvent.getEventID());
            secondEventCheck = eDao.QueryEvent(secondEvent.getEventID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertTrue(didItWork);
        assertNull(bestEventCheck);
        assertNull(secondEventCheck);

    }
    @Test
    public void testClearAllPassSec() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        Event bestEventCheck = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.ClearTableEvents();
            bestEventCheck = eDao.QueryEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertTrue(didItWork);
        assertNull(bestEventCheck);

    }
    @Test
    public void testReturnAllPass() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        EventDao eDao = null;
        ArrayList <Event> compareTest = null;
        ArrayList <Event> origTest = new ArrayList<>();
        origTest.add(bestEvent);
        origTest.add(thirdEvent);
        try {
            Connection conn = db.openConnection();
            eDao = new EventDao(conn);
            eDao.AddEvent(bestEvent);
            eDao.AddEvent(secondEvent);
            eDao.AddEvent(thirdEvent);
            compareTest = eDao.ReturnAllEvents(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when adding only one User");
        }
        assertTrue(didItWork);
        assertEquals(compareTest, origTest);
    }
    @Test
    public void testReturnAllFamilyFail() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        EventDao eDao = null;
        ArrayList <Event> compareTest = null;
        try {
            Connection conn = db.openConnection();
            eDao = new EventDao(conn);
            eDao.AddEvent(secondEvent);
            compareTest = eDao.ReturnAllEvents(secondPerson.getPersonID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when adding only one User");
        }
        assertTrue(didItWork);
        assertNull(compareTest);
        //fix this
    }
}
