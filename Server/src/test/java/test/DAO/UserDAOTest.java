package test.DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User theUser;
    private UserDAO uDAO;

    @BeforeEach
    public void setup() throws DataAccessException {

        //Create a new data base object
        db = new Database();
        //Create a randomly filled Person object to test
        theUser = new User("captain", "avengers", "cap50@avengers.org", "Steve", "Rogers", "m", "CaptainAmerica");
        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        uDAO = new UserDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        uDAO.clear();
        //use daos to set up
        //close the connection at end of setup before doing service stuff SET TO TRUE
        //using persons service you need to create people first

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
        uDAO.insert(theUser);
        // Let's use a find method to get the event that we just put in back out.
        User userCompare = uDAO.find(theUser.getUsername());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(userCompare);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(theUser, userCompare);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //Insert the user into the table
        uDAO.insert(theUser);
        //Test to see if exception is thrown because you cant add two of the same users
        assertThrows(DataAccessException.class, () -> uDAO.insert(theUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        //Insert user
        uDAO.insert(theUser);
        //Find the user and look to see if it is null
        User userCompare = uDAO.find(theUser.getUsername());
        assertNotNull(userCompare);
        //If it isn't null check to see if the one we found is the same as the original user "theUser"
        assertEquals(theUser, userCompare);
    }

    @Test
    public void findFail() throws DataAccessException {
        //Insert user
        uDAO.insert(theUser);
        //Find a user that is not in the database
        assertNull(uDAO.find("ironman"));
    }

    @Test
    public void clearTest() throws DataAccessException {
        //Insert several users
        User user2 = new User("ironman", "roxs", "ironman@gmail.com", "Tony", "Stark", "m", "IronMan");
        uDAO.insert(theUser);
        uDAO.insert(user2);
        //Clear the user table
        uDAO.clear();
        //Try to find both users
        assertNull(uDAO.find(theUser.getUsername()));
        assertNull(uDAO.find(user2.getUsername()));
    }
}
