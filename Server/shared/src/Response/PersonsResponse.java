package Response;

import Model.Person;

public class PersonsResponse {

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
     * Array of persons called data
     */
    private Person [] data;

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
     * @param message message for the user
     * @param success success of request
     */
    public PersonsResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructor
     * @param data array of Persons
     * @param success success of request
     */
    public PersonsResponse(Person[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

}
