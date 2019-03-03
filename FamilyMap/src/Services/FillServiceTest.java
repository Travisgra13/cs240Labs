package Services;

import Model.Event;
import Model.Person;
import Model.User;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.After;
import org.junit.Before;

public class FillServiceTest {
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
}
