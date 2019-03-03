package dataAccess;
import java.sql.*;

public class Database {
    private Connection conn;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {
        openConnection();
        try (Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS User " +
                    "(" +
                    "UserName text PRIMARY KEY NOT NULL," +
                    "Password text NOT NULL," +
                    "Email text NOT NULL," +
                    "FirstName text NOT NULL," +
                    "LastName text NOT NULL," +
                    "Gender text," +
                    "PersonID text NOT NULL" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS Person " +
                    "(" +
                    "PersonID text PRIMARY KEY NOT NULL," +
                    "Descendant text NOT NULL," +
                    "FirstName text NOT NULL," +
                    "LastName text NOT NULL," +
                    "Gender text NOT NULL," +
                    "Father text," +
                    "Mother text," +
                    "Spouse text" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS Event " +
                    "(" +
                    "EventID text PRIMARY KEY NOT NULL," +
                    "Descendant text," +
                    "Person text," +
                    "Latitude text," +
                    "Longitude text," +
                    "Country text," +
                    "City text," +
                    "EventType text," +
                    "Year integer" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS Tokens " +
                    "(" +
                    "Key text PRIMARY KEY NOT NULL," +
                    "User text NOT NULL" +
                    ")";
            stmt.executeUpdate(sql);
            closeConnection(true);
        }catch (DataAccessException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while creating tables");
        }
    }

    public void clearTables() throws DataAccessException {
        openConnection();
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Event;" +
                    "DELETE FROM User;" +
                    "DELETE FROM Person;" +
                    "DELETE FROM Tokens";
            stmt.executeUpdate(sql);
            closeConnection(true);
        }catch (DataAccessException e) {
            closeConnection(false);
            throw e;
        }catch (SQLException e) {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

}
