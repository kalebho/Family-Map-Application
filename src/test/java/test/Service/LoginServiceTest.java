package test.Service;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Response.RegisterResponse;
import Services.LoginService;
import Services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginServiceTest {

    private LoginRequest loginRequest1;
    private RegisterRequest registerRequest;
    private UserDAO uDAO;
    private Database db;

    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException {

        //Create database and open connection
        db = new Database();
        db.openConnection();
        //set connection in the uDAO
        uDAO = new UserDAO(db.getConnection());
        //Clear out the users in the database
        uDAO.clear();
        //close the connection and commit changes
        db.closeConnection(true);
        //Register a person in the database
        registerRequest = new RegisterRequest("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse  = registerService.register(registerRequest);
        //Create loging request that will be used to login
        loginRequest1 = new LoginRequest("kalebho", "Laiehawaii");
    }

    @Test
    public void loginPass() throws DataAccessException {
        //create login service and login
        LoginService loginService = new LoginService();
        LoginResponse loginResponse = loginService.login(loginRequest1);
        //Test to see the response is true
        assertTrue(loginResponse.isSuccess());
    }

    @Test
    public void loginFail() throws DataAccessException {
        //Login request with wrong password
        LoginRequest loginRequest2 = new LoginRequest("kalebho", "yummy");
        //create login service and login with wrong password
        LoginService loginService = new LoginService();
        LoginResponse loginResponse = loginService.login(loginRequest2);
        //Test to see the response is false
        assertFalse(loginResponse.isSuccess());
    }

}
