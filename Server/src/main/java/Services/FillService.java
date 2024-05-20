package Services;

import DAO.*;
import Generate.CreateGenerations;
import Model.Event;
import Model.Person;
import Response.FillResponse;
import Response.RegisterResponse;

import java.io.FileNotFoundException;


/**
 * Used to call methods on DAO objects to fill a certain users data with their input data
 */
public class FillService {

    /**
     * Takes in a FillRequest and performs a fill using the given data in the request
     * @return FillResponse that tells whether the login was successful or not
     */
    public FillResponse fill(String username, int numGenerations) throws DataAccessException, FileNotFoundException {

        Database db = new Database();

        try {

            //open connection
            db.openConnection();

            //DAOs that will use
            UserDAO uDAO = new UserDAO(db.getConnection());
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            EventDAO eDAO = new EventDAO(db.getConnection());
            //Check that the numGenerations is positive and the username exists
            if ((numGenerations <= 0) || (uDAO.find(username) == null)) {
                throw new DataAccessException("Invalid user or number of generations");
            }
            //Check that the user is in the database already
            if (uDAO.find(username) != null) {
                //If there is data already for the user....Delete it!!!
                pDAO.delete(username);
                eDAO.delete(username);
            }

            //Then fill the data with random generation
            CreateGenerations createGenerations = new CreateGenerations();
            createGenerations.generatePerson(uDAO.find(username), numGenerations, 2000);

            //Insert all the people that were created
            for (Person currPerson : createGenerations.getAddedPersons()) {
                pDAO.insert(currPerson);
            }
            //Insert all the Events that were made
            for (Event currEvent : createGenerations.getAddedEvents()) {
                eDAO.insert(currEvent);
            }

            //Close connection and commit the actions
            db.closeConnection(true);
            FillResponse fillResponse = new FillResponse("Successfully added " + createGenerations.getAddedPersons().size() +
                    " persons and " + createGenerations.getAddedEvents().size() + " events to the database", true);
            return fillResponse;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            FillResponse fillResponse = new FillResponse("Error: " + e.getMessage(), false);
            return fillResponse;
        }
    }





}
