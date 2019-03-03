package Result;
/**
 * Holds the response body for PersonID
 */
public class PersonIDResult {
    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;
    /**
     * User (Username) to which this person belongs
     */
    private String descendant;
    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;
    /**
     * Person’s last name (non-empty string)
     */
    private String lastName;
    /**
     * Person’s gender (string: f or m)
     */
    private String gender;
    /**
     * ID of person’s father (possibly null)
     */
    private String father;
    /**
     * ID of person’s mother (possibly null)
     */
    private String mother;
    /**
     * ID of person’s spouse (possibly null)
     */
    private String spouse;
    /**
     *whether or not the response was successful
     */
    private boolean successful;

    private String message;

    public PersonIDResult(String descendant, String personID, String firstName, String lastName, String gender, String father, String mother, String spouse, boolean successful) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.successful = successful;
        setMessage();
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * This returns the message associated with the result
     * @return
     */
    public void setMessage() {
        if (successful) {
            message = "Valid Auth Token";
        }
        else {
            message = "Error";
        }

    }
}
