package Model;

import java.util.ArrayList;
/**
 * This class is the model class that holds the Person information
 */
public class User {
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
     *User’s gender (string: f or m)
     */
    private String gender;
    /**
     *Unique Person ID assigned to this user’s generated Person object - see Family
     *History Information section for details (non-empty string)
     */
    private String personID;


    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
    }
    public void SetGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonID() {
        return personID;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User newUser = (User) o;
            if (newUser.getUserName().equals(getUserName()) &&
                    newUser.getPassword().equals(getPassword()) &&
                    newUser.getEmail().equals(getEmail()) &&
                    newUser.getFirstName().equals(getFirstName()) &&
                    newUser.getLastName().equals(getLastName()) &&
                    newUser.getGender().equals(getGender()) &&
                    newUser.getPersonID().equals(getPersonID())) {
                return true;
            }
        }
        return false;
    }
}
