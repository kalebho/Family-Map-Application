package Services;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.LoadResponse;

/**
 * Used to call methods on DAO objects to clear the database and load the input data
 */
public class LoadService {

    /**
     * Takes in a LoadRequest and performs a load using the given data in the request
     * @param r user LoadRequest input
     * @return LoadResponse that tells whether the login was successful or not
     */
    public LoadResponse load(LoadRequest r) throws DataAccessException {

        Database db = new Database();

        try {

            //open connection
            db.openConnection();

            //create DAOs that will use
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            UserDAO uDAO = new UserDAO(db.getConnection());
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            EventDAO eDAO = new EventDAO(db.getConnection());

            //call functions to clear all data
            aDAO.clear();
            uDAO.clear();
            pDAO.clear();
            eDAO.clear();

            //For loop to loop through the users and add them
            for (User currUser : r.getUsers()) {
                uDAO.insert(currUser);
            }
            if (r.getUsers().length == 0) {
                throw new DataAccessException("User array is empty");
            }
            //For loop to loop through the persons and add them
            for (Person currPerson : r.getPersons()) {
                pDAO.insert(currPerson);
            }
            if (r.getPersons().length == 0) {
                throw new DataAccessException("Person array is empty");
            }
            //For loop to loop through the events and add them
            for (Event currEvent : r.getEvents()) {
                eDAO.insert(currEvent);
            }
            if (r.getEvents().length == 0) {
                throw new DataAccessException("Event array is empty");
            }

            //close connection and commit
            db.closeConnection(true);

            LoadResponse loadResponse = new LoadResponse("Successfully added " + r.getUsers().length + " users, " +
                    r.getPersons().length + " persons, and " + r.getEvents().length + " events to the database.", true);
            return loadResponse;

        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //close and rollback
            db.closeConnection(false);

            LoadResponse loadResponse = new LoadResponse("Error: " + e.getMessage(), false);

            return loadResponse;
        }
    }
}
