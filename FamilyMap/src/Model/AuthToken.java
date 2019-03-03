package Model;

/**
 * This is the authToken model class
 */
public class AuthToken {
    /**
     * Non-empty auth token string
     */
    private String key;
    /**
     *  User name passed in with request (Non-empty)
     */
    private String user;

    public AuthToken(String key, String user) {
        this.key = key;
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public String getUser() {
        return user;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        AuthToken newAuthToken = (AuthToken) o;
        if (newAuthToken.getKey().equals(getKey()) && newAuthToken.getUser().equals(getUser())) {
            return true;
        }
        else {
            return false;
        }
    }
}
