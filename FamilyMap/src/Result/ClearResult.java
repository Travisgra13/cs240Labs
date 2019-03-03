package Result;
/**
 * Holds the response body for Clear
 */
public class ClearResult {
    /**
     * whether or not the response was successful
     */
    private boolean successful;

    public ClearResult(boolean successful) {
        this.successful = successful;
    }

    /**
     * This returns the message associated with the result
     * @return
     */
    public String getMessage() {
        //check if successful and if so print out success message
        //if not return error message
        return null;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
