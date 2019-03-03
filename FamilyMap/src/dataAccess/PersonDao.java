package dataAccess;

import Model.Person;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is full of methods key to manipulating the sql database and
 * the model objects of the type Person
 */
public class PersonDao {
    private Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }
    /**
     * This adds a person into the SQL database
     * @param myPerson the person to be added
     * @return true or false based on whether it failed or not
     */
    public boolean AddPerson(Person myPerson) throws DataAccessException{
        boolean commit = true;
        String sql = "INSERT INTO Person (PersonID, Descendant, FirstName, LastName, Gender, Father, Mother, Spouse)" +
           "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myPerson.getPersonID());
            stmt.setString(2, myPerson.getDescendant());
            stmt.setString(3, myPerson.getFirstName());
            stmt.setString(4, myPerson.getLastName());
            stmt.setString(5, myPerson.getGender());
            stmt.setString(6, myPerson.getFather());
            stmt.setString(7, myPerson.getMother());
            stmt.setString(8, myPerson.getSpouse());

            stmt.executeUpdate();
        }catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
        //add person to SQL db and returns true if successful
        return commit;
    }
    /**
     * This finds a person object and returns the object
     * @param myPersonID the person to be queried
     * @return the Person object found, if not found null is returned
     */
    public Person QueryPerson(String myPersonID) throws DataAccessException {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, myPersonID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                person = new Person(rs.getString("PersonID"), rs.getString("Descendant"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("Father"),
                        rs.getString("Mother"), rs.getString("Spouse"));
            }
        }catch(SQLException e) {
            throw new DataAccessException("Error encountered while finding person");
        }
        //Check if unique personID is found and return the person if found
        return person;
    }
    /**
     * Deletes a person from the database
     * @param myPersonID the person to be deleted
     * @return true or false based on whether it is successful or not
     */
    public boolean DeletePerson(String myPersonID) throws DataAccessException{
        boolean commit = true;
        String sql = "DELETE FROM Person WHERE PersonID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (QueryPerson(myPersonID) == null) {
                throw new SQLException();
            }
            stmt.setString(1, myPersonID);
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting user" + myPersonID);
        }
        //Search for unique userName and delete, return true if found, if not return false
        return commit;
    }
    /**
     * Clears the database and Model objects that are of the type Person
     * @return true or false based on whether it is successful or not
     */
    public boolean ClearTablePersons() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Person";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }catch(SQLException e) {
            commit = false;
            throw new DataAccessException("Error in deleting all users");
        }
        return commit;
    }
}
