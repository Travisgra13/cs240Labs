package Model;

/**
 * This class is the model class that holds the Person information
 */
public class Person {
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

    public Person(String personID, String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }
    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Person) {
            Person newPerson = (Person) o;
            if (newPerson.getPersonID().equals(getPersonID()) &&
                    newPerson.getDescendant().equals(getDescendant()) &&
                    newPerson.getFirstName().equals(getFirstName()) &&
                    newPerson.getLastName().equals(getLastName()) &&
                    newPerson.getGender().equals(getGender()) &&
                    newPerson.getFather().equals(getFather()) &&
                    newPerson.getMother().equals(getMother()) &&
                    newPerson.getSpouse().equals(getSpouse()))
                return true;
        }
        return false;
    }
}
