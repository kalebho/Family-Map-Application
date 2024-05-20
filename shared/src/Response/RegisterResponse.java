package Response;

/**
 * Response sent to client from server to register a new user
 */
public class RegisterResponse {

    /**
     * Creates a RegisterResponse that initializes its data members
     * @param authtoken response authtoken
     * @param username response username
     * @param personID response ID
     * @param success response success
     */
    public RegisterResponse(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public RegisterResponse(String message, boolean success) {
        this.success = success;
        this.message = message;
    }

    /**
     * user's authtoken
     */
    private String authtoken;

    /**
     * user's username
     */
    private String username;

    /**
     * user's ID
     */
    private String personID;

    /**
     * success result
     */
    private  boolean success;

    /**
     * error message
     */
    private String message;


    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
