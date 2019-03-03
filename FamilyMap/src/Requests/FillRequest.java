package Requests;

/**
 * Holds the request body for Fill
 */
public class FillRequest {
    /**
     * Non-empty string
     */
    private String userName;
    /**
     * Year the event occurred (integer)
     */
    private Integer numGenerations;

    public FillRequest(String userName, Integer numGenerations) {
        this.userName = userName;
        this.numGenerations = numGenerations;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(Integer numGenerations) {
        this.numGenerations = numGenerations;
    }
}
