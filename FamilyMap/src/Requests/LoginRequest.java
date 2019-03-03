package Requests;

/**
 * Holds the request body for Login
 */
public class LoginRequest {
    /**
     *  Unique user name (non-empty string)
     */
    private String userName;
    /**
     * User’s password (non-empty string)
     */
    private String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
