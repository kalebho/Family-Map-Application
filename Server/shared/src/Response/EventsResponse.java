package Response;

import Model.Event;

public class EventsResponse {

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
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
    private Event [] data;

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
     * @param data array of Events
     * @param success success of request
     */
    public EventsResponse(Event[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    /**
     * Constructor
     * @param message error message
     * @param success success of request
     */
    public EventsResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
