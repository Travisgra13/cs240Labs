package Result;
/**
 * Holds the response body for Fill
 */
public class FillResult {
    /**
     * whether or not the response was successful
     */
    private boolean successful;

    public FillResult(boolean successful) {
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
}
