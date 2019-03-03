package Services;

import Model.Person;
import Model.User;
import Requests.PersonIDRequest;
import Requests.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;

public class PersonServiceTest {
    Database db;
    User bestUser;
    User secondUser;
    Person bestPerson;
    Person secondPerson;
    Person thirdPerson;
    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("Obama", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
        secondUser = new User("Donald16", "theWall", "potus21@gmail.com", "Donald", "Trump", "m", "bestPrez");
        bestPerson = new Person("travy", "Obama", "Travis", "Graham", "m", "Craig", "Lakesha", "Conn");
        secondPerson = new Person("cardi", "Obama", "Cardi", "B", "f", "Lorice", "Johnisha", "Gordon");
        thirdPerson = new Person("sam", "Obama", "Samantha", "Thomas", "f", "Royce", "Tory", "Kim");

        db.createTables();
    }
    @After
    public void tearDown() throws DataAccessException {
        db.clearTables();
    }
    @Test
    public void personTestPass() throws DataAccessException {
        db.clearTables();
        ArrayList <Person> persons = new ArrayList<>();
        persons.add(bestPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);
        PersonRequest personRequest = new PersonRequest(bestUser.getUserName());
        PersonResult expectedResult = new PersonResult(persons, true);
        PersonResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(persons.get(0));
            pDao.AddPerson(persons.get(1));
            pDao.AddPerson(persons.get(2));
            uDao.AddUser(bestUser);
            db.closeConnection(true);
            PersonService personService = new PersonService(db);
            acutalResult = personService.person(personRequest);
            db.closeConnection(true);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(acutalResult.isSuccessful());
        assertEquals(expectedResult.isSuccessful(), acutalResult.isSuccessful());
        assertTrue(expectedResult.getFamilyMembers().equals(acutalResult.getFamilyMembers()));
    }

    @Test
    public void personTestFail() throws DataAccessException {
        db.clearTables();
        ArrayList <Person> persons = new ArrayList<>();
        PersonRequest personRequest = new PersonRequest(secondUser.getUserName());
        PersonResult expectedResult = new PersonResult(persons, true);
        PersonResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            pDao.AddPerson(thirdPerson);
            uDao.AddUser(secondUser);
            db.closeConnection(true);
            PersonService personService = new PersonService(db);
            acutalResult = personService.person(personRequest);
            db.closeConnection(true);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(acutalResult.isSuccessful());
        assertEquals(expectedResult.isSuccessful(), acutalResult.isSuccessful());
        assertTrue(expectedResult.getFamilyMembers().equals(acutalResult.getFamilyMembers()));
    }
    @Test
    public void personIDTestPass() throws DataAccessException {
        db.clearTables();
        PersonIDRequest personIDRequest = new PersonIDRequest(bestPerson.getPersonID());
        PersonIDResult expectedResult = new PersonIDResult(bestPerson.getDescendant(), bestPerson.getPersonID(), bestPerson.getFirstName(), bestPerson.getLastName(), bestPerson.getGender(),
                bestPerson.getFather(),bestPerson.getMother(), bestPerson.getSpouse(),true);
        PersonIDResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            pDao.AddPerson(thirdPerson);
            db.closeConnection(true);
            db.openConnection();
            PersonService personService = new PersonService(db);
            acutalResult = personService.personID(personIDRequest);
            db.closeConnection(true);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(acutalResult.isSuccessful());
        assertEquals(expectedResult.isSuccessful(), acutalResult.isSuccessful());
        assertTrue(expectedResult.getPersonID().equals(acutalResult.getPersonID()));
    }
    @Test
    public void personIDTestFail() throws DataAccessException {
        db.clearTables();
        PersonIDRequest personIDRequest = new PersonIDRequest(secondUser.getPersonID());
        PersonIDResult expectedResult = new PersonIDResult(null, null, null, null, null,
                null, null, null, false);
        PersonIDResult acutalResult = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(thirdPerson);
            db.closeConnection(true);
            db.openConnection();
            PersonService personService = new PersonService(db);
            acutalResult = personService.personID(personIDRequest);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertFalse(acutalResult.isSuccessful());
        assertEquals(expectedResult.isSuccessful(), acutalResult.isSuccessful());
    }
}
