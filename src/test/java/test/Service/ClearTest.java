package test.Service;


import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.ClearResponse;
import Response.LoginResponse;
import Response.RegisterResponse;
import Services.ClearService;
import Services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {

    private User user;

    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException {

        //clear out the current data
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = clearService.clear();
        //Register a person in the database and create Data
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse  = registerService.register(registerRequest);
        //Save the user of the person who registered
        Database db = new Database();
        db.openConnection();
        UserDAO uDAO = new UserDAO(db.getConnection());
        user = uDAO.find("kalebho");
        db.closeConnection(true);
    }

    @Test
    public void clearPass1() throws DataAccessException {
        //create clear service and clear the data
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = clearService.clear();
        //Try to get a user from the Data.....This should return null
        Database db = new Database();
        db.openConnection();
        UserDAO uDAO = new UserDAO(db.getConnection());
        assertNull(uDAO.find("kaleb"));
        db.closeConnection(true);
    }

    @Test
    public void clearPass2() throws DataAccessException {
        //create clear service and clear the data
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = clearService.clear();
        //Try to get a person from the Data using the user who registered and then was cleared.....This should return null
        Database db = new Database();
        db.openConnection();
        PersonDAO pDAO = new PersonDAO(db.getConnection());
        //Try to find the person that represents the user that just registered and then was cleared
        assertNull(pDAO.find(user.getPersonID()));
        db.closeConnection(true);
    }


}
