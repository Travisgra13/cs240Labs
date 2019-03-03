package Services;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Result.LoadResult;
import Result.LoginResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;

public class LoadServiceTest {
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
        bestEvent = new Event("happs54", "thomas4", "travy", 10595456.02,1943265.58,"U.S.A.", "provo", "baptism", 2003);
        secondEvent = new Event("happs589", "johnny3", "cardi", 10595456.85,1943265.98,"U.S.A.", "provo", "birth", 2005);
        thirdEvent = new Event("happenings", "thomas4", "sam", 85785.96,74865.28,"Mexico", "Rin", "death", 1720);
        db.createTables();
    }
    @After
    public void tearDown() throws DataAccessException {
        db.clearTables();
    }
    @Test
    public void loadServicePass() throws DataAccessException{
        User[] myUsers = new User[2];
        myUsers[0] = bestUser;
        myUsers[1] = secondUser;
        Person[] myPersons = new Person[3];
        myPersons[0] = bestPerson;
        myPersons[1] = secondPerson;
        myPersons[2] = thirdPerson;
        Event[] myEvents = new Event[3];
        myEvents[0] = bestEvent;
        myEvents[1] = secondEvent;
        myEvents[2] = thirdEvent;
        LoadRequest loadRequest = new LoadRequest(myUsers, myPersons, myEvents);
        LoadResult expectedResult = new LoadResult(true, 2,3,3);
        LoadResult loadResult = null;
        try {
            Connection conn = db.openConnection();
            LoadService loadService = new LoadService(db);
            loadResult = loadService.load(loadRequest);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw new DataAccessException("Problem in loadServiceFunc");
        }
        assertNotNull(loadResult);
        assertTrue(expectedResult.getMessage().equals(loadResult.getMessage()));
    }
    @Test
    public void loadServiceFail() throws DataAccessException{
        User[] myUsers = null;
        Person[] myPersons = new Person[3];
        myPersons[0] = bestPerson;
        myPersons[1] = secondPerson;
        myPersons[2] = thirdPerson;
        Event[] myEvents = new Event[3];
        myEvents[0] = bestEvent;
        myEvents[1] = secondEvent;
        myEvents[2] = thirdEvent;
        LoadRequest loadRequest = new LoadRequest(myUsers, myPersons, myEvents);
        LoadResult expectedResult = new LoadResult(false, 0,0,0);
        LoadResult loadResult = null;
        try {
            Connection conn = db.openConnection();
            LoadService loadService = new LoadService(db);
            loadResult = loadService.load(loadRequest);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            throw new DataAccessException("Problem in loadServiceFunc");
        }
        assertNotNull(loadResult);
        assertTrue(expectedResult.getMessage().equals(loadResult.getMessage()));

    }
}

