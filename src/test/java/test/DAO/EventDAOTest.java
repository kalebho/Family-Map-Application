package test.DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest {

    private Database db;
    private Event theEvent;
    private EventDAO eDAO;

    @BeforeEach
    public void setup() throws DataAccessException {

        //Create a new data base object
        db = new Database();
        //Create a randomly filled Event object to test
        theEvent = new Event("bBall", "kobe", "mamba", 24.02F, 8.24F, "US", "Los Angeles", "Game", 2016);
        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDAO = new EventDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDAO.clear();

    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDAO.insert(theEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event eventCompare = eDAO.find(theEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(eventCompare);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(theEvent, eventCompare);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //Insert the user into the table
        eDAO.insert(theEvent);
        //Test to see if exception is thrown because you cant add two of the same users
        assertThrows(DataAccessException.class, () -> eDAO.insert(theEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        //Insert user
        eDAO.insert(theEvent);
        //Find the user and look to see if it is null
        Event eventCompare = eDAO.find(theEvent.getEventID());
        assertNotNull(eventCompare);
        //If it isn't null check to see if the one we found is the same as the original user "theUser"
        assertEquals(theEvent, eventCompare);
    }

    @Test
    public void findFail() throws DataAccessException {
        //Insert user
        eDAO.insert(theEvent);
        //Find a user that is not in the database
        assertNull(eDAO.find("cruise"));
    }

    @Test
    public void findAllPass() throws DataAccessException {
        //Array of persons returned
        Event[] eventArray;
        Event event2 = new Event("Another", "kobe", "Shaq", 30.8F, 17F, "America", "Phily", "Ball", 2024);
        // Start by inserting an event into the database.
        eDAO.insert(theEvent);
        eDAO.insert(event2);
        // Let's use a find method to get the event that we just put in back out.
        eventArray = eDAO.findAll("kobe");
        assertEquals(2, eventArray.length);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        //Array of persons returned
        Event[] eventArray;
        Event event2 = new Event("Another", "kobe", "Shaq", 30.8F, 17F, "America", "Phily", "Ball", 2024);
        // Start by inserting an event into the database.
        eDAO.insert(theEvent);
        eDAO.insert(event2);
        // Let's use a find method to get the event that we just put in back out.
        eventArray = eDAO.findAll("kawai");
        assertEquals(0, eventArray.length);
    }

    @Test
    public void deletePass() throws DataAccessException {
        Event event2 = new Event("Another", "kobe", "Shaq", 30.8F, 17F, "America", "Phily", "Ball", 2024);
        // Start by inserting an event into the database.
        eDAO.insert(theEvent);
        eDAO.insert(event2);
        // Delete using correct associatedUsername
        eDAO.delete("kobe");
        //result should be null
        assertNull(eDAO.find("Another"));
        assertNull(eDAO.find("bBall"));
    }

    @Test
    public void deleteFail() throws DataAccessException {
        Event event2 = new Event("Another", "kobe", "Shaq", 30.8F, 17F, "America", "Phily", "Ball", 2024);
        // Start by inserting an event into the database.
        eDAO.insert(theEvent);
        eDAO.insert(event2);
        // Delete using correct associatedUsername
        eDAO.delete("swish");
        //result should be null
        assertNotNull(eDAO.find("Another"));
        assertNotNull(eDAO.find("bBall"));
    }

    @Test
    public void clearTest() throws DataAccessException {
        //Insert Event
        eDAO.insert(theEvent);
        //Clear the user table
        eDAO.clear();
        //Try to find both users
        assertNull(eDAO.find(theEvent.getEventID()));
    }

}
