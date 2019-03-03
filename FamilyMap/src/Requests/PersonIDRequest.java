package Requests;
/**
 * Holds the request body for PersonID
 */
public class PersonIDRequest {
    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;

    public PersonIDRequest(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

}
