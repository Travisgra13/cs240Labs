package dataAccess;

import Model.AuthToken;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is full of methods key to manipulating the sql database and
 * the model objects of the type AuthToken
 */
public class AuthTokenDao {
    private Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }
    /**
     * This adds an authorization token into the SQL database
     * @return true or false based on whether it failed or not
     */
    public String AddAuthToken(String userName) throws DataAccessException{
        String sql = "INSERT INTO Tokens (Key, User)" + " VALUES(?,?)";
        UUID uuid = UUID.randomUUID();
        String randomToken = uuid.toString();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, randomToken);
            stmt.setString(2, userName);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Error when adding a new AuthToken");
        }
        //add authorization token to SQL db and returns true if successful
        return randomToken;
    }

    /**
     * This validates whether the token is correct for the present user
     * @param myTokenKey the token to be inspected
     * @param userName the userName the token is to be checked in conjunction with the token
     * @return true or false based on whether it is successful or not
     */
    public boolean CorrectAuthToken(String myTokenKey, String userName) throws DataAccessException {
        //check if authToken matches with user
        String sql = "SELECT * FROM Tokens WHERE Key=? AND User=?";
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myTokenKey);
            stmt.setString(2, userName);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                return true;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Error when adding a new AuthToken");
        }
        return false;
    }

    /**
     * This finds a token and returns the AuthToken Array associated with a user
     * @param userName the userName to be queried
     * @return the authToken found, if not found null is returned
     */
    public ArrayList<AuthToken> QueryAuthToken(String userName) throws DataAccessException {
        String sql = "SELECT * FROM Tokens WHERE User=?";
        ArrayList <AuthToken> userAuthTokens = new ArrayList<>();
        AuthToken myAuthToken = null;
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                myAuthToken = new AuthToken(rs.getString("key"),rs.getString("user"));
                userAuthTokens.add(myAuthToken);
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error when querying AuthToken");
        }
        if (userAuthTokens.size() == 0) {
            return null;
        }
        else {
            return userAuthTokens;
        }
        //Check if unique token is found and return the token if found
    }

    public String QueryAuthTokenByToken(String token) throws DataAccessException {
        String sql = "SELECT * FROM Tokens WHERE Key=?";
        AuthToken myAuthToken = null;
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                myAuthToken = new AuthToken(rs.getString("key"),rs.getString("user"));
                return myAuthToken.getUser();
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error when querying AuthToken");
        }
            return null;
        //Check if unique token is found and return the token if found
    }

    /**
     * Deletes an auth token from the database
     * @param userName the token to be deleted
     * @return true or false based on whether it is successful or not
     */
    public boolean DeleteAuthToken(String userName) throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Tokens WHERE User=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (QueryAuthToken(userName) == null) {
                throw new SQLException();
            }
            stmt.setString(1, userName);
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting AuthToken for " + userName);
        }
        //Search for unique userName and delete, return true if found, if not return false
        return commit;
    }

    /**
     * Clears the database and Model objects that are of the type AuthToken
     * @return true or false based on whether it is successful or not
     */
    public boolean ClearTableTokens() throws DataAccessException{
        boolean commit = true;
        String sql = "DELETE FROM Tokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }catch (SQLException e) {
            commit = false;
            throw new DataAccessException();
        }
        //go through all entries in sql database and clear delete all
        return commit;
    }

}
