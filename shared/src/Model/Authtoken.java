package Model;


import java.util.Objects;

/**
 * Authtoken class used for each user
 */
public class Authtoken {

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

    /**
     * Unique authtoken
     */
    private String authtoken;

    /**
     * Username associated with the authtoken
     */
    private String username;

    /**
     * Creates an Authtoken initializing the authtoken and username
     * @param authtoken authtoken
     * @param username associated username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authtoken = (Authtoken) o;
        return Objects.equals(((Authtoken)o).authtoken, authtoken.authtoken) && Objects.equals(username, authtoken.username);
    }
}
