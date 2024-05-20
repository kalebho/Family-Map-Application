package test.DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class PersonDAOTest {

    private Database db;
    private Person thePerson;
    private PersonDAO pDAO;

    @BeforeEach
    public void setup() throws DataAccessException {

        //Create a new data base object
        db = new Database();
        //Create a randomly filled Person object to test
        thePerson = new Person("captain", "cap", "Steve", "Rogers", "m", "papa", "mama", "carter");
        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDAO = new PersonDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        pDAO.clear();

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
        pDAO.insert(thePerson);
        // Let's use a find method to get the event that we just put in back out.
        Person personCompare = pDAO.find(thePerson.getPersonID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(personCompare);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(thePerson, personCompare);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> pDAO.insert(thePerson));

    }

    @Test
    public void findPass() throws DataAccessException {
        Person person2 = new Person("thor", "thorOdinson", "Thor", "Odinson", "m", "odin", "queen", "jane");
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        // Let's use a find method to get the event that we just put in back out.
        Person personCompare = pDAO.find(person2.getPersonID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(personCompare);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(person2, personCompare);
    }

    @Test
    public void findFail() throws DataAccessException{
        //insert the person into the database
        pDAO.insert(thePerson);
        //We are going to find a using a personID that isn't in the database
        //and it should throw an exception
        assertNull(pDAO.find("thor"));
    }

    @Test
    public void findAllPass() throws DataAccessException {
        //Array of persons returned
        Person [] personArray;
        Person person2 = new Person("thor", "cap", "Thor", "Odinson", "m", "odin", "queen", "jane");
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        // Let's use a find method to get the event that we just put in back out.
        personArray = pDAO.findAll("cap");
        assertEquals(2, personArray.length);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        //Array of persons returned
        Person [] personArray;
        Person person2 = new Person("thor", "cap", "Thor", "Odinson", "m", "odin", "queen", "jane");
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        // Let's use a find method to get the event that we just put in back out.
        personArray = pDAO.findAll("thor");
        assertEquals(0, personArray.length);
    }

    @Test
    public void deletePass() throws DataAccessException {
        Person person2 = new Person("thor", "cap", "Thor", "Odinson", "m", "odin", "queen", "jane");
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        //Delete all persons with the associatedUsername of "cap"
        pDAO.delete("cap");
        //check to see if person with "captain" Id still in the database...It shouldn't be
        assertNull(pDAO.find("captain"));
        assertNull(pDAO.find("thor"));
    }

    @Test
    public void deleteFail() throws DataAccessException {
        Person person2 = new Person("thor", "cap", "Thor", "Odinson", "m", "odin", "queen", "jane");
        // Start by inserting an event into the database.
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        //Delete all persons with the associatedUsername of "cap"....this is wrong associatedUsername
        pDAO.delete("thor");
        //check to see if person with "captain" Id still in the database...It should be
        assertNotNull(pDAO.find("captain"));
    }

    @Test
    public void clearTest() throws DataAccessException {
        Person person2 = new Person("thor", "thorOdinson", "Thor", "Odinson", "m", "odin", "queen", "jane");
        //insert two persons
        pDAO.insert(thePerson);
        pDAO.insert(person2);
        //clear the database person table
        pDAO.clear();
        //Check if both are no longer in the person table
        assertNull(pDAO.find(thePerson.getPersonID()));
        assertNull(pDAO.find(person2.getPersonID()));
    }

}
