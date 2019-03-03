package dataAccess;

import Model.Person;
import java.sql.Connection;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonDaoTest {
    Database db;
    Person bestPerson;
    Person secondPerson;
    Person thirdPerson;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestPerson = new Person("travy", "thomas4", "Travis", "Graham", "m", "Craig", "Lakesha", "Conn");
        secondPerson = new Person("cardi", "thomas4", "Cardi", "B", "f", "Lorice", "Johnisha", "Gordon");
        thirdPerson = new Person("sam", "thomas4", "Samantha", "Thomas", "f", "Royce", "Tory", "Kim");
        db.createTables();
    }
    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }
    @Test
    public void insertPass() throws Exception {
        Person comparePerson = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            comparePerson = pDao.QueryPerson(bestPerson.getPersonID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(comparePerson);
        assertEquals(bestPerson, comparePerson);
    }
    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(bestPerson);
            db.closeConnection(didItWork);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Person comparePerson = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            comparePerson = pDao.QueryPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(comparePerson);
    }

    @Test
    public void testQueryPerson() throws DataAccessException{
        Person comparePerson = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            comparePerson = pDao.QueryPerson(secondPerson.getPersonID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(comparePerson);
        assertEquals(secondPerson, comparePerson);
    }
    @Test
    public void testFailQueryPerson() throws DataAccessException {
        boolean didItWork = true;
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            compareTest = pDao.QueryPerson(thirdPerson.getPersonID());
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertNull(compareTest);
    }
    @Test
    public void testDeletePersonPass() throws DataAccessException{
        Person comparePerson = null;
        boolean didItWork = true;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            pDao.AddPerson(thirdPerson);
            pDao.DeletePerson(bestPerson.getPersonID());
            comparePerson = pDao.QueryPerson(bestPerson.getPersonID());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertNull(comparePerson);
        assertTrue(didItWork);
    }
    @Test
    public void testDeleteFail() throws DataAccessException {
        boolean didItWork = true;
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            compareTest = pDao.QueryPerson(thirdPerson.getPersonID());
            didItWork = pDao.DeletePerson(thirdPerson.getPersonID());
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
        Person bestPersonCheck = null;
        Person secondPersonCheck = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            pDao.ClearTablePersons();
            bestPersonCheck = pDao.QueryPerson(bestPerson.getPersonID());
            secondPersonCheck = pDao.QueryPerson(secondPerson.getPersonID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertTrue(didItWork);
        assertNull(bestPersonCheck);
        assertNull(secondPersonCheck);

    }

    @Test
    public void testClearAllPassSec() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        PersonDao pDao = null;
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.ClearTablePersons();
            compareTest = pDao.QueryPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when adding only one User");
        }
        assertTrue(didItWork);
        assertNull(compareTest);
    }
}
