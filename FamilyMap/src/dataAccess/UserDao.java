package dataAccess;

import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * This class is full of methods key to manipulating the sql database and
 * the model objects of the type User
 */
public class UserDao {
    private Connection conn;
    public UserDao(Connection conn) {
        this.conn = conn;
    }
    /**
     * This adds a user into the SQL database
     * @param myUser the user to be added
     * @return true or false based on whether it failed or not
     */
    public boolean AddUser(User myUser) throws DataAccessException {
        boolean commit = true;
        String sql = "INSERT INTO User (UserName, Password, Email, FirstName, LastName, Gender, PersonID)" +
                "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myUser.getUserName());
            stmt.setString(2, myUser.getPassword());
            stmt.setString(3, myUser.getEmail());
            stmt.setString(4, myUser.getFirstName());
            stmt.setString(5, myUser.getLastName());
            stmt.setString(6, myUser.getGender());
            stmt.setString(7, myUser.getPersonID());

            stmt.executeUpdate();
        }catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting User into the database");
        }
        //add User to SQL db and returns true if successful
        return commit;
    }
    /**
     * This finds a User object and returns the object
     * @param myUserName the user to be queried
     * @return the User object found, if not found null is returned
     */

    public User QueryUser(String myUserName) throws DataAccessException {
        User user = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myUserName);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                user = new User(rs.getString("UserName"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("PersonID"));
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error encountered while finding user");

        }
        //Check if unique userName is found and return the user if found
        return user;
    }
    /**
     * Deletes a User from the database
     * @param myUserName the User to be deleted
     * @return true or false based on whether it is successful or not
     */
    public boolean DeleteUser(String myUserName) throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM User WHERE UserName=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (QueryUser(myUserName) == null) {
                throw new SQLException();
            }
            stmt.setString(1, myUserName);
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting user" + myUserName);
        }
        //Search for unique userName and delete, return true if found, if not return false
        return commit;
    }
    /**
     * Clears the database and Model objects that are of the type User
     * @return true or false based on whether it is successful or not
     */
    public boolean ClearTableUsers() throws DataAccessException{
        boolean commit = true;
        String sql = "DELETE FROM User";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting all users");
        }
        return commit;
    }
    /**
     * This returns all people from all family members of the user
     * @param myUserName the userName to which the family members will be searched for
     * @return an ArrayList of Person objects that contain all events
     */
    public ArrayList<Person> ReturnAllFamilyMembers(String myUserName) throws DataAccessException{
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE Descendant = ?;";
        ArrayList<Person> familyMembers = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myUserName);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                person = new Person(rs.getString("PersonID"), rs.getString("descendant"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("father"),
                        rs.getString("mother"), rs.getString("spouse"));
                familyMembers.add(person);
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error encountered while finding user");

        }
        //Check if unique userName is found and return the user if found
        return familyMembers;
    }

}
