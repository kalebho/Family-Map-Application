package Response;

import Model.Person;

/**
 * Response sent to client from server for retrieving 1 or multiple persons
 */
public class PersonResponse {
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Person's ID
     */
    private String personID;

    /**
     * Username that is associated with this person in their tree
     */
    private String associatedUsername;

    /**
     * First name of the person
     */
    private String firstName;

    /**
     * Last name of the person
     */
    private String lastName;

    /**
     * Gender of the person
     */
    private String gender;

    /**
     * ID of the person's father if there is one
     */
    private String fatherID;

    /**
     * ID of the person's mother if there is one
     */
    private String motherID;

    /**
     * ID of the person's spouse if there is one
     */
    private String spouseID;

    /**
     * messgae of the response
     */
    private String message;

    /**
     * success of the request
     */
    private boolean success;

    /**
     * Constructor
     * @param personID personID
     * @param associatedUsername username associated with user
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender gender
     * @param fatherID fathers ID
     * @param motherID mothers ID
     * @param spouseID spouses ID
     * @param success success of the request
     */
    public PersonResponse(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    public  PersonResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
