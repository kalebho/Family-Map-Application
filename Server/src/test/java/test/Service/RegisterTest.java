package test.Service;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Request.RegisterRequest;
import Response.RegisterResponse;
import Services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private RegisterRequest registerRequest;
    private UserDAO uDAO;
    private Database db;


    @BeforeEach
    public void setup() throws DataAccessException {
        //Create database and open connection
        db = new Database();
        db.openConnection();
        //set connection in the uDAO
        uDAO = new UserDAO(db.getConnection());
        //Clear out the users in the database
        uDAO.clear();
        //Create a randomly filled registerRequest object to test to be passed into the register method
        registerRequest = new RegisterRequest("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m");
        //close the connection and commit changes
        db.closeConnection(true);
    }

    @Test
    public void registerPass() throws DataAccessException, FileNotFoundException {
        //Make a registerService object and registerResponse object
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse = null;
        //Call register on the object
        registerResponse = registerService.register(registerRequest);
        //Check to see if response was successful
        assertEquals(true, registerResponse.isSuccess());
    }

    @Test
    public void registerFail() throws DataAccessException, FileNotFoundException {
        //Make a registerService object and registerResponse object
        RegisterService registerService = new RegisterService();
        //First regResponse which should be successful
        RegisterResponse registerResponse1 = null;
        //Second regResoponse that is a fail because we register the same user
        RegisterResponse registerResponse2 = null;
        //Call register on the object twice two get a success and a fail response
        registerResponse1 = registerService.register(registerRequest);
        registerResponse2 = registerService.register(registerRequest);

        //Check to see if 2nd regResponse is false
        assertFalse( registerResponse2.isSuccess());
    }

}
