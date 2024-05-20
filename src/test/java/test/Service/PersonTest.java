package test.Service;

import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.*;
import Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonTest {

    private String theAuthtoken;

    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException {

        //clear out the current data
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = clearService.clear();

        //Register a single user associated with the one of the people created below
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse  = registerService.register(registerRequest);

        //Loging the user
        LoginRequest loginRequest = new LoginRequest("kalebho", "Laiehawaii");
        LoginService loginService = new LoginService();
        LoginResponse loginResponse = loginService.login(loginRequest);
        //Save the authtoken used at login
        theAuthtoken = loginResponse.getAuthtoken();


        //Create 2 random persons
        Database db = new Database();
        db.openConnection();
        PersonDAO pDAO = new PersonDAO(db.getConnection());
        Person person1 = new Person("Mosese_ID", "kalebho", "Mosese", "Heimuli", "m", "Melo_ID", "Cecilia_ID", "Meeks_ID");
        Person person2 = new Person("Tony", "tony24", "Tony", "Stark", "m", "Howard_ID", "Maria_ID", "Pepper_ID");
        pDAO.insert(person1);
        pDAO.insert(person2);
        db.closeConnection(true);
    }

    @Test
    public void personPass() throws DataAccessException {
        PersonService personService = new PersonService();
        PersonResponse personResponse = personService.getSinglePerson("Mosese_ID", theAuthtoken);
        //Test to see the response is true
        assertTrue(personResponse.isSuccess());
    }

    @Test
    public void personFail() throws DataAccessException {
        PersonService personService = new PersonService();
        //Try to get a person with the wrong authtoken
        PersonResponse personResponse = personService.getSinglePerson("Mosese_ID", "wrong authtoken");
        //Test to see the response is true
        assertFalse(personResponse.isSuccess());
    }
}
