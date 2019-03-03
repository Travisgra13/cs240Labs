package dataAccess;

import Model.Person;
import Model.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.*;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;

public class UserDaoTest {
    Database db;
    User bestUser;
    User secondUser;
    User thirdUser;
    Person myFirstPerson;
    Person mySecondPerson;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("Obama", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
        secondUser = new User("Donald16", "theWall", "potus21@gmail.com", "Donald", "Trump", "m", "bestPrez");
        thirdUser = new User("Danny Phantom", "ghosting", "ghost@gmail.com", "Danny", "Phantom", "m", "dannyPhan3");
        myFirstPerson = new Person("testie", bestUser.getUserName(), "Test", "Great", "male", "John", "Connor", "tease");
        mySecondPerson = new Person("testie2", bestUser.getUserName(), "Great", "Test", "male", "Tom", "Hanks", "Erin");
        db.createTables();
    }
    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }
    @Test
    public void insertPass() throws Exception {
        User compareUser = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            compareUser = uDao.QueryUser(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareUser);
        assertEquals(bestUser, compareUser);
    }
    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(bestUser);
            db.closeConnection(didItWork);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        User compareUser = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            compareUser = uDao.QueryUser(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareUser);
    }

    @Test
    public void testQueryUser() throws DataAccessException{
        User compareUser = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            compareUser = uDao.QueryUser(secondUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(compareUser);
        assertEquals(secondUser, compareUser);
    }
    @Test
    public void testFailQueryUser() throws DataAccessException {
        boolean didItWork = true;
        User compareTest = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            compareTest = uDao.QueryUser(thirdUser.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertNull(compareTest);
    }
    @Test
    public void testDeleteUserPass() throws DataAccessException{
        User compareUser = null;
        boolean didItWork = true;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            uDao.AddUser(thirdUser);
            uDao.DeleteUser(bestUser.getUserName());
            compareUser = uDao.QueryUser(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertNull(compareUser);
        assertTrue(didItWork);
    }
    @Test
    public void testDeleteFail() throws DataAccessException {
        boolean didItWork = true;
        User compareTest = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            compareTest = uDao.QueryUser(thirdUser.getUserName());
            didItWork = uDao.DeleteUser(thirdUser.getUserName());
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
        User bestUserCheck = null;
        User secondUserCheck = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            uDao.ClearTableUsers();
            bestUserCheck = uDao.QueryUser(bestUser.getUserName());
            secondUserCheck = uDao.QueryUser(secondUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertNull(bestUserCheck);
        assertNull(secondUserCheck);

    }
    @Test
    public void testClearAllPassSec() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        UserDao uDao = null;
        User compareTest = null;
        try {
            Connection conn = db.openConnection();
            uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            uDao.ClearTableUsers();
            compareTest = uDao.QueryUser(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when adding only one User");
        }
        assertNull(compareTest);
    }
    @Test
    public void testReturnAllFamilyPass() throws DataAccessException{
        boolean didItWork = true;
        db.clearTables();
        UserDao uDao = null;
        ArrayList <Person> compareTest = null;
        ArrayList <Person> origTest = new ArrayList<>();
        origTest.add(myFirstPerson);
        origTest.add(mySecondPerson);
        Connection tempConn = db.openConnection();
        PersonDao pDao = new PersonDao(tempConn);
        pDao.AddPerson(myFirstPerson);
        pDao.AddPerson(mySecondPerson);
        db.closeConnection(true);
        try {
            Connection conn = db.openConnection();

            uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            compareTest = uDao.ReturnAllFamilyMembers(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when returning family members");
        }
        assertTrue(didItWork);
        assertEquals(origTest, compareTest);
    }
    @Test
    public void testReturnAllFamilyFail() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        UserDao uDao = null;
        ArrayList <Person> compareTest = null;
        ArrayList <Person> test = new ArrayList<>();
        Connection tempConn = db.openConnection();
        PersonDao pDao = new PersonDao(tempConn);
        pDao.AddPerson(myFirstPerson);
        pDao.AddPerson(mySecondPerson);
        db.closeConnection(true);
        try {
            Connection conn = db.openConnection();

            uDao = new UserDao(conn);
            uDao.AddUser(secondUser);
            compareTest = uDao.ReturnAllFamilyMembers(secondUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when returning Users");
        }
        assertTrue(didItWork);
        assertEquals(compareTest, test);
        //fix this
    }
}
