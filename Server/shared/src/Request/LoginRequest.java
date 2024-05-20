package Request;

/**
 * Request sent to server from client to login a user
 */
public class LoginRequest {

    /**
     * Create a LoginRequest initializing all its data members
     * @param username login username
     * @param password login password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Request username
     */
    private String username;

    /**
     * Request password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
