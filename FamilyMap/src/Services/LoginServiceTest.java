package Services;

import Model.User;
import Requests.LoginRequest;
import Result.LoginResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginServiceTest {
    Database db;
    User bestUser;
    User secondUser;
   @Before
   public void setUp() throws Exception {
       db = new Database();
       bestUser = new User("Obama", "twerk216", "thebestPresident@gmail.com","Barack","Obama", "m","potus");
       secondUser = new User("Donald16", "theWall", "potus21@gmail.com", "Donald", "Trump", "m", "bestPrez");
       db.createTables();
   }
   @After
    public void tearDown() throws DataAccessException {
       db.clearTables();
   }
   @Test
    public void loginServiceTestPass() throws DataAccessException {
       LoginRequest loginRequest = new LoginRequest(bestUser.getUserName(), bestUser.getPassword());
       LoginResult loginResult = null;
       try {
           Connection conn = db.openConnection();
           UserDao uDao = new UserDao(conn);
           uDao.AddUser(bestUser);
           db.closeConnection(true);
           LoginService loginService = new LoginService(db);
           db.openConnection();
           loginResult = loginService.login(loginRequest);
          // db.closeConnection(true);
       }catch (DataAccessException e) {
           e.printStackTrace();
           db.closeConnection(false);
           throw new DataAccessException("Error in loginServiceTestPass");
       }
       assertTrue(loginResult.isSuccessful());
       assertNotNull(loginResult.getAuthToken());
       assertEquals(bestUser.getUserName(), loginResult.getUserName());
       assertEquals(bestUser.getPersonID(), loginResult.getPersonID());
   }
    @Test
    public void loginServiceTestFail() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest(bestUser.getUserName(), "FakePassword1234");
        LoginResult loginResult = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.AddUser(bestUser);
            db.closeConnection(true);
            LoginService loginService = new LoginService(db);
            db.openConnection();
            loginResult = loginService.login(loginRequest);
            // db.closeConnection(true);
        }catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error in loginServiceTestPass");
        }
        assertFalse(loginResult.isSuccessful());
        assertNull(loginResult.getAuthToken());
        assertEquals(bestUser.getUserName(), loginResult.getUserName());
    }
}
