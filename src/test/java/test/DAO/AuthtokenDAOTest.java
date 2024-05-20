package test.DAO;

import DAO.AuthtokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import Model.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDAOTest {

    private Database db;
    private Authtoken theAuthtoken;
    private AuthtokenDAO aDAO;

    @BeforeEach
    public void setup() throws DataAccessException {

        //Create a new data base object
        db = new Database();
        //Create a randomly filled Person object to test
        theAuthtoken = new Authtoken("mine", "yours");
        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        aDAO = new AuthtokenDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        aDAO.clear();

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
        aDAO.insert(theAuthtoken);
        // Let's use a find method to get the event that we just put in back out.
        Authtoken authtokenCompare = aDAO.find(theAuthtoken.getAuthtoken());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(authtokenCompare);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(theAuthtoken, authtokenCompare);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //Insert the user into the table
        aDAO.insert(theAuthtoken);
        //Test to see if exception is thrown because you cant add two of the same users
        assertThrows(DataAccessException.class, () -> aDAO.insert(theAuthtoken));
    }

    @Test
    public void findPass() throws DataAccessException {
        //Insert user
        aDAO.insert(theAuthtoken);
        //Find the user and look to see if it is null
        Authtoken authtokenCompare = aDAO.find(theAuthtoken.getAuthtoken());
        assertNotNull(authtokenCompare);
        //If it isn't null check to see if the one we found is the same as the original user "theUser"
        assertEquals(theAuthtoken, authtokenCompare);
    }

    @Test
    public void findFail() throws DataAccessException {
        //Insert user
        aDAO.insert(theAuthtoken);
        //Find a user that is not in the database
        assertNull(aDAO.find("ironman"));
    }

    @Test
    public void clearTest() throws DataAccessException {
        //Insert several users
        aDAO.insert(theAuthtoken);
        //Clear the user table
        aDAO.clear();
        //Try to find both users
        assertNull(aDAO.find(theAuthtoken.getAuthtoken()));
    }


}
