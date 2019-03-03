package Requests;
/**
 * Holds the request body for Person
 */
public class PersonRequest {
    /**
     * Unique user name (non-empty string)
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PersonRequest(String userName) {
        this.userName = userName;
    }
}
