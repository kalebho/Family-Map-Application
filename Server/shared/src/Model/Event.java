package Model;

import java.util.Objects;

/**
 * Event class for each person in the tree to explain their life events
 */
public class Event {

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
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }



}
