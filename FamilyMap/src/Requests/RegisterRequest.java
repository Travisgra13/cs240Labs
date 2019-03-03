package Requests;
/**
 * Holds the request body for Register
 */
public class RegisterRequest {
    /**
     * Unique user name (non-empty string)
     */
    private String userName;
    /**
     * User’s password (non-empty string)
     */
    private String password;
    /**
     * User’s email address (non-empty string)
     */
    private String email;
    /**
     * User’s first name (non-empty string)
     */
    private String firstName;
    /**
     * User’s last name (non-empty string)
     */
    private String lastName;
    /**
     * User’s gender (string: f or m)
     */
    private String gender;


    public RegisterRequest(String userName, String password, String email, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
