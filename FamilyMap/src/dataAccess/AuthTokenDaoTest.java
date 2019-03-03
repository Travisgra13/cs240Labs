package dataAccess;

import Model.AuthToken;
import Model.Person;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class AuthTokenDaoTest {
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
    public void tearDown() throws Exception {
        db.clearTables();
    }
    @Test
    public void insertPass() throws Exception {
        ArrayList <AuthToken> compareAuthToken = null;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            compareAuthToken = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareAuthToken);
    }
    @Test
    public void insertMultTokensForSameUser() throws Exception {
        db.clearTables();
        boolean didItWork = true;
        ArrayList <AuthToken> compareTest = new ArrayList<>();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.AddAuthToken(bestUser.getUserName());
            db.closeConnection(didItWork);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertTrue(didItWork);
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
             compareTest = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(compareTest.get(0).getUser(), compareTest.get(1).getUser());
    }
    @Test
    public void CorrectAuthTokenPass() throws DataAccessException {
        boolean didItWork = true;
        AuthToken compareAuthToken = null;
        String generatedAuthToken = "";
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            compareAuthToken = aDao.QueryAuthToken(bestUser.getUserName()).get(0);
            generatedAuthToken = compareAuthToken.getKey();
            didItWork = aDao.CorrectAuthToken(generatedAuthToken, bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException();
        }
        assertNotNull(compareAuthToken);
        assertTrue(didItWork);
    }
    @Test
    public void CorrectAuthTokenFail() throws DataAccessException {
        boolean didItWork = true;
        AuthToken compareAuthToken = null;
        String generatedAuthToken = "";
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            compareAuthToken = aDao.QueryAuthToken(bestUser.getUserName()).get(0);
            generatedAuthToken = compareAuthToken.getKey();
            didItWork = aDao.CorrectAuthToken(generatedAuthToken, secondUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException();
        }
        assertNotNull(compareAuthToken);
        assertFalse(didItWork);
    }
    @Test
    public void testQueryAuthTokenByKeyPass() throws DataAccessException {
        boolean didItWork = true;
        String compareToken = null;
        String expectedUser = null;

        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            AuthToken myToken = aDao.QueryAuthToken(bestUser.getUserName()).get(0);
            compareToken = aDao.QueryAuthTokenByToken(myToken.getKey());
            expectedUser = myToken.getUser();
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(compareToken);
        assertTrue(didItWork);
        assertEquals(expectedUser, compareToken);
    }
    @Test
    public void testQueryAuthTokenByKeyFail() throws DataAccessException {
        boolean didItWork = true;
        String compareToken = null;
        String expectedUser = null;

        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(secondUser.getUserName());
            AuthToken myToken = aDao.QueryAuthToken(secondUser.getUserName()).get(0);
            compareToken = aDao.QueryAuthTokenByToken("Thisisatest");
            expectedUser = myToken.getUser();
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNull(compareToken);
        assertTrue(didItWork);
        assertNotEquals(expectedUser, compareToken);
    }
    @Test
    public void testQueryAuthToken() throws DataAccessException{
        boolean didItWork = true;
        ArrayList <AuthToken> compareTokens = new ArrayList<>();
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            compareTokens = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(compareTokens);
        assertTrue(didItWork);
        assertEquals(1, compareTokens.size());
    }
    @Test
    public void testQueryAuthTokenFail() throws DataAccessException{
        boolean didItWork = true;
        ArrayList <AuthToken> compareTokens = new ArrayList<>();
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(secondUser.getUserName());
            compareTokens = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNull(compareTokens);
        assertTrue(didItWork);
    }
    @Test
    public void testDeleteAuthTokenPass() throws DataAccessException{
        ArrayList <AuthToken> compareTest = null;
        boolean didItWork = true;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.DeleteAuthToken(bestUser.getUserName());
            compareTest = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertTrue(didItWork);
    }
    @Test
    public void testDeleteAuthTokenFail() throws DataAccessException{
        ArrayList <AuthToken> compareTest = null;
        boolean didItWork = true;
        db.clearTables();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.DeleteAuthToken(secondUser.getUserName());
            compareTest = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);

        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertFalse(didItWork);
    }
    @Test
    public void testClearAllPass() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        ArrayList <AuthToken> bestAuthTokenCheck = new ArrayList<>();
        ArrayList <AuthToken> secondAuthTokenCheck = new ArrayList<>();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.AddAuthToken(secondUser.getUserName());
            aDao.ClearTableTokens();
            bestAuthTokenCheck = aDao.QueryAuthToken(bestUser.getUserName());
            secondAuthTokenCheck = aDao.QueryAuthToken(secondUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertTrue(didItWork);
        assertNull(bestAuthTokenCheck);
        assertNull(secondAuthTokenCheck);

    }
    @Test
    public void testClearAllPassSec() throws DataAccessException {
        boolean didItWork = true;
        db.clearTables();
        ArrayList <AuthToken> bestAuthTokenCheck = new ArrayList<>();
        try {
            Connection conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            aDao.AddAuthToken(bestUser.getUserName());
            aDao.ClearTableTokens();
            bestAuthTokenCheck = aDao.QueryAuthToken(bestUser.getUserName());
            db.closeConnection(true);
        }catch(DataAccessException e) {
            didItWork = false;
            db.closeConnection(false);
            throw new DataAccessException("Failure when clearing all tables");
        }
        assertTrue(didItWork);

    }
}
