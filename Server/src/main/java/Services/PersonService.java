package Services;

import DAO.AuthtokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.Authtoken;
import Model.Person;
import Response.PersonResponse;
import Response.PersonsResponse;

/**
 * Used to retrieve a single person's (or multiple people's) events, name, fatherID, etc.
 */
public class PersonService {

    /**
     * Used to get a single person using that person's ID
     * @param personID specific ID
     * @param authtoken authtoken used to get the right person
     * @return A person response or an error based on the missing or wrong information
     */
    public PersonResponse getSinglePerson(String personID, String authtoken) throws DataAccessException {
        //Create Database object
        Database db = new Database();
        try {
            //Open Database
            db.openConnection();
            //create DAOs to use for getting, setting, and clearing
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            //make sure authtoken is exists using ".find"
            if (aDAO.find(authtoken) == null) {
                throw new DataAccessException("Invalid Authtoken");
            }
            //make sure personID exists
            if (pDAO.find(personID) == null) {
                throw new DataAccessException("Invalid PersonID");
            }
            Authtoken authResult = aDAO.find(authtoken);
            Person personResult = pDAO.find(personID);
            //after getting person make sure that username from authtoken and associatedusername is same
            if (!authResult.getUsername().equals(personResult.getAssociatedUsername())) {
                throw new DataAccessException("Person not associated with user");
            }
            //Close and commit transaction
            db.closeConnection(true);
            //Create and return successful person response object
            PersonResponse pRes = new PersonResponse(personResult.getPersonID(), personResult.getAssociatedUsername(), personResult.getFirstName(), personResult.getLastName(),
                    personResult.getGender(), personResult.getFatherID(), personResult.getMotherID(), personResult.getSpouseID(), true);
            return pRes;
        }
        catch (DataAccessException e) {

            e.printStackTrace();

            //Close and rollback transaction
            db.closeConnection(false);

            //Create and return failure person response object
            PersonResponse pRes = new PersonResponse("Error: " + e.getMessage(), false);
            return pRes;
        }
    }

    /**
     * Used to get multiple persons using an array of person IDs
     * @param authtoken authtoken for the user to get their persons
     * @return the array of specific persons
     */
    public PersonsResponse getPersons(String authtoken) throws DataAccessException{

        //Create Database object
        Database db = new Database();

        try {
            //open Database
            db.openConnection();

            //Create DAOs that I will use
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            if (aDAO.find(authtoken) == null) {
                throw new DataAccessException("Invalid authtoken");
            }
            //Getting the username from the authtoken
            String associatedUsername = aDAO.find(authtoken).getUsername();
            //Save the persons in an array
            Person [] personsResult = pDAO.findAll(associatedUsername);
            //close the connection and make response
            db.closeConnection(true);
            PersonsResponse pRes = new PersonsResponse(personsResult,true);
            return pRes;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close and rollback transaction
            db.closeConnection(false);

            //Create and return failure person response object
            PersonsResponse pRes = new PersonsResponse("Error: " + e.getMessage(), false);
            return pRes;
        }
    }
}
