package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Request sent to server from client to clear the data and input the users' data
 */
public class LoadRequest {

    /**
     * Create a LoadRequest initializing all its data members
     * @param user user array
     * @param person person array
     * @param event event array
     */
    public LoadRequest(User[] user, Person[] person, Event[] event) {
        this.users = user;
        this.persons = person;
        this.events = event;
    }

    /**
     * Array of users
     */
    private User [] users;

    /**
     * Array of persons for each user
     */
    private Person [] persons;

    /**
     * Array of events for each person
     */
    private Event [] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
