package Request;

/**
 * Request sent to server from client to register a new user
 */
public class RegisterRequest {

    /**
     * Create a RegisterRequest with all data members initialized
     * @param username request username
     * @param password request password
     * @param email request email
     * @param firstName request first name
     * @param lastName request last name
     * @param gender request gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Username from client request
     */
    private String username;

    /**
     * password from client request
     */
    private String password;

    /**
     * email from client request
     */
    private String email;

    /**
     * first name from client request
     */
    private String firstName;

    /**
     * last name from client request
     */
    private String lastName;

    /**
     * gender from client request
     */
    private String gender;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
