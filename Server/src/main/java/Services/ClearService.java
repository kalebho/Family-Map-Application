package Services;

import DAO.*;
import Response.ClearResponse;
import Response.RegisterResponse;

/**
 * Used to call methods on DAO objects to clear the entire database
 */

public class ClearService {

    /**
     * Used to clear the entire database
     */
    public ClearResponse clear() throws DataAccessException {
        //create Database object
        Database db = new Database();

        try {
            //open connection
            db.openConnection();

            //Create DAOs to use for clearing
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            UserDAO uDAO = new UserDAO(db.getConnection());
            PersonDAO pDAO = new PersonDAO(db.getConnection());
            EventDAO eDAO = new EventDAO(db.getConnection());

            //call functions to clear all data
            aDAO.clear();
            uDAO.clear();
            pDAO.clear();
            eDAO.clear();

            //close connection and commit
            db.closeConnection(true);

            //create and send back response
            ClearResponse clearResponse = new ClearResponse("Clear succeeded.", true);
            return clearResponse;

        }
        catch (DataAccessException e) {
            e.printStackTrace();
            //close connection and rollback
            db.closeConnection(false);

            ClearResponse registerResponse = new ClearResponse("Error: " + e.getMessage(), false);

        }
        return null;
    }
}
