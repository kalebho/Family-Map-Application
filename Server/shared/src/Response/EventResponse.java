package Response;

/**
 * Response sent to client from server for retrieving 1 or multiple events
 */
public class EventResponse {

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
     * ID of the event
     */
    private String eventID;

    /**
     * Username associated with the event that happened to a person in the tree
     */
    private String associatedUsername;

    /**
     * ID of the person who the event belongs to
     */
    private String personID;

    /**
     * Latitude of the event
     */
    private float latitude;

    /**
     * Longitude of the event
     */
    private float longitude;

    /**
     * County in which the event happened
     */
    private String country;

    /**
     * City in which it happened
     */
    private String city;

    /**
     * Kind of event
     */
    private String eventType;

    /**
     * Year in which it happened
     */
    private int year;

    /**
     * Message for user
     */
    private String message;

    /**
     * Success of request
     */
    private boolean success;


    /**
     * Creates an event with ID, latitude, longitude, country, city, and year
     * @param eventID ID
     * @param associatedUsername username of person associated with event
     * @param personID ID of person associated with event
     * @param latitude latitude
     * @param longitude longitude
     * @param country country where the event happens
     * @param city city where the event happens
     * @param eventType kind of event
     * @param year which year it happens
     * @param success success of the request
     */
    public EventResponse(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    public EventResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


}
