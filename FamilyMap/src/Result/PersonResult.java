package Result;

import Model.Person;

import java.util.ArrayList;
/**
 * Holds the response body for Person
 */
public class PersonResult {
    /**
     * the list of people that are family members
     */
    private ArrayList<Person> familyMembers;
    /**
     *whether or not the response was successful
     */
    private boolean successful;
    private String message;

    public PersonResult(ArrayList<Person> familyMembers, boolean successful) {
        this.familyMembers = familyMembers;
        this.successful = successful;
        setMessage();
    }
    /**
     * This returns the message associated with the result
     * @return
     */
    public void setMessage() {
       if(!successful) {
           message = "Invalid Auth Token";
       }
       else {
           message = "Valid Token";
       }
    }

    public ArrayList<Person> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(ArrayList<Person> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
