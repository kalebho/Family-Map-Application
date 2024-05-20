package Services;

import DAO.AuthtokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Generate.RandomID;
import Model.Authtoken;
import Model.User;
import Request.LoginRequest;
import Response.LoginResponse;
import Response.PersonResponse;
import Response.PersonsResponse;
import Response.RegisterResponse;

/**
 * Used to call methods on DAO objects to perform user login
 */
public class LoginService {
    /**
     * Takes in a LoginRequest and performs a login using the given data in the request
     * @param r user LoginRequest input
     * @return LoginResponse that tells whether the login was successful or not
     */
    public LoginResponse login(LoginRequest r) throws DataAccessException {

        //Create Database object
        Database db = new Database();
        try {
            //open the connection
            db.openConnection();
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            UserDAO uDAO = new UserDAO(db.getConnection());
            //Check if the user is actually in the database
            if (uDAO.find(r.getUsername()) == null) {
                throw new DataAccessException("No user found");
            }
            //Check if the person logging in has the right password
            User helperUser = uDAO.find(r.getUsername());
            String passwordCompare = helperUser.getPassword();
            if (!r.getPassword().equals(passwordCompare)) {
                throw new DataAccessException("Incorrect password");
            }
            //Make a random authtoken and add to table
            RandomID randomID = new RandomID();
            String authtoken = randomID.generateRandomID();
            Authtoken newAuthtoken = new Authtoken(authtoken, r.getUsername());
            aDAO.insert(newAuthtoken);
            //Close connection and commit the actions
            db.closeConnection(true);
            LoginResponse loginResponse = new LoginResponse(authtoken, r.getUsername(), helperUser.getPersonID(), true);
            return loginResponse;

        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close and rollback transaction
            db.closeConnection(false);

            //Create and return failure person response object
            LoginResponse loginResponse = new LoginResponse("Error: " + e.getMessage(), false);
            return loginResponse;
        }
    }
}
