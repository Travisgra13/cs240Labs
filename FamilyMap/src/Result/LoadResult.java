package Result;
/**
 * Holds the response body for Load
 */
public class LoadResult {
    /**
     * whether or not the response was successful
     */
    /*
    private boolean successful;
    private int numUsers;
    private int numPersons;
    private int numEvents;*/
    private String message;
/*
    public boolean isSuccessful() {
        return successful;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public int getNumEvents() {
        return numEvents;
    }
*/
    public LoadResult(boolean successful, int numUsers, int numPersons, int numEvents) {
        setMessage(successful, numUsers, numPersons, numEvents);
    }

    /**
     * This returns the message associated with the result
     * @return
     */
    public void setMessage(boolean successful, int numUsers, int numPersons, int numEvents) {
        if (successful) {
            message = "Successfully added " + numUsers + " users, " +
                    numPersons + " persons, and " + numEvents + " events to the database.";
        }
        else {
            message = "Error Invalid Request Data";
        }
        //check if successful and if so print out success message
        //if not return error message
    }
    public String getMessage() {
        return message;
    }
}
