package Services;

import Model.Person;
import Model.User;
import Result.ClearResult;
import Result.LoginResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClearServiceTest {
    Database db;
    User bestUser;
    User secondUser;
    Person bestPerson;
    Person secondPerson;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("Obama", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
        secondUser = new User("Donald16", "theWall", "potus21@gmail.com", "Donald", "Trump", "m", "bestPrez");
        bestPerson = new Person("travy", "thomas4", "Travis", "Graham", "m", "Craig", "Lakesha", "Conn");
        secondPerson = new Person("cardi", "thomas4", "Cardi", "B", "f", "Lorice", "Johnisha", "Gordon");
        db.createTables();
    }
    @After
    public void tearDown() throws DataAccessException {
        db.clearTables();
    }
    @Test
    public void clearServicePass() throws DataAccessException {
        ClearResult clearResult = new ClearResult(true);
        User compareUser = null;
        LoginResult loginResult = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            db.closeConnection(true);
            ClearService clearService = new ClearService(db);
            clearResult = clearService.clear();
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(clearResult.isSuccessful());
        assertNotNull(clearResult);
    }
    @Test
    public void clearServicePassSec() throws DataAccessException {
        ClearResult clearResult = new ClearResult(true);
        User compareUser = null;
        LoginResult loginResult = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            uDao.AddUser(bestUser);
            uDao.AddUser(secondUser);
            pDao.AddPerson(bestPerson);
            pDao.AddPerson(secondPerson);
            db.closeConnection(true);
            ClearService clearService = new ClearService(db);
            clearResult = clearService.clear();
            conn = db.openConnection();
            uDao = new UserDao(conn);
            compareUser = uDao.QueryUser(secondUser.getUserName());
            db.closeConnection(true);

        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertTrue(clearResult.isSuccessful());
        assertNotNull(clearResult);
        assertNull(compareUser);
    }
}
