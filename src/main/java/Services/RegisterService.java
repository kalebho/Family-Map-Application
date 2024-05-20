package Services;

import DAO.*;
import Generate.CreateGenerations;
import Generate.RandomID;
import Model.Authtoken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Response.RegisterResponse;

import java.io.FileNotFoundException;

/**
 * Used to call methods on DAO objects to perform user registration
 */
public class RegisterService {

    /**
     * Takes in a RegisterRequest and performs a registration using the given data in the request
     * @param r user RegisterRequest input
     * @return RegisterResponse that tells whether the register was successful or not
     */
    public RegisterResponse register(RegisterRequest r) throws DataAccessException, FileNotFoundException {
        //Create Database object
        Database db = new Database();
        RegisterResponse theRegResponse = new RegisterResponse("message", true);
        try {
            //Open Database
            db.openConnection();
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            UserDAO uDAO = new UserDAO(db.getConnection());
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            EventDAO eDAO = new EventDAO(db.getConnection());
            //Check to see if username already in the system
            if (uDAO.find(r.getUsername()) != null) {
                throw new DataAccessException("Username already in the database");
            }
            //create random personID
            RandomID randomID = new RandomID();
            String personID = randomID.generateRandomID();
            //Create a user
            User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
            //Insert the user into the user table
            uDAO.insert(newUser);
            //Create Person for the user and generations of the user using this function
            //Pass in the new user to generatePerson and 4 numGenerations
            CreateGenerations createGenerations = new CreateGenerations();
            createGenerations.generatePerson(newUser, 4, 2000);     //This will add people and events to an array and then I need to loop through and add them
            //Insert all the people that were created
            for (Person currPerson : createGenerations.getAddedPersons()) {
                pDAO.insert(currPerson);
            }
            //Insert all the Events that were made
            for (Event currEvent : createGenerations.getAddedEvents()) {
                eDAO.insert(currEvent);
            }
            //LOGGING IN BEGIN
            //Use login service to log the person you just created
            //Make a random authtoken and add to table
            RandomID authRandomID = new RandomID();
            String authtoken = authRandomID.generateRandomID();
            Authtoken newAuthtoken = new Authtoken(authtoken, r.getUsername());
            aDAO.insert(newAuthtoken);
            //END
            //Close connection and commit the actions
            db.closeConnection(true);
            RegisterResponse registerResponse = new RegisterResponse(authtoken, r.getUsername(), personID, true);
            return registerResponse;
        }
        catch (DataAccessException e) {

            e.printStackTrace();

            //Close and commit rollback
            db.closeConnection(false);

            RegisterResponse registerResponse = new RegisterResponse("Error: " + e.getMessage(), false);
            return registerResponse;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return theRegResponse;
    }
}
