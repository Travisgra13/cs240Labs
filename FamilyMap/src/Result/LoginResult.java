package Result;
/**
 * Holds the response body for Login
 */
public class LoginResult {
    /**
     * Non-empty auth token string
     */
    private String authToken;
    /**
     * User name passed in with request
     */
    private String userName;
    /**
     *Non-empty string containing the Person ID of the
     *user’s generated Person object
     */
    private String personID;
    /**
     * whether or not the response was successful
     */
    private boolean successful;

    public LoginResult(String authToken, String userName, String personID, boolean successful) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.successful = successful;

    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
    /**
     * This returns the message associated with the result
     * @return
     */
    public String getMessage() {
        return null;
    }
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
