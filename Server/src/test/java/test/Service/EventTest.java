package test.Service;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import Model.Event;
import Model.Person;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.*;
import Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

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


        //Create 2 random events
        Database db = new Database();
        db.openConnection();
        EventDAO eDAO = new EventDAO(db.getConnection());
        Event event1 = new Event("Play_ID", "kahi", "Koa_ID", 5.0F, 0.0F, "USA", "Honolulu", "Football", 2016);
        Event event2 = new Event("Swim_ID", "kalebho", "Jay_ID", 3.0F, 8.0F, "USA", "Laie", "Batch", 2020);
        eDAO.insert(event1);
        eDAO.insert(event2);
        db.closeConnection(true);
    }

    @Test
    public void personPass() throws DataAccessException {
        EventService eventService = new EventService();
        //Test finding the event associated with logged in user
        EventResponse eventResponse = eventService.getSingleEvent("Swim_ID", theAuthtoken);
        //Test to see the response is true
        assertTrue(eventResponse.isSuccess());
    }

    @Test
    public void personFail() throws DataAccessException {
        EventService eventService = new EventService();
        //Test finding the event not associated with logged in user
        EventResponse eventResponse = eventService.getSingleEvent("Play_ID", theAuthtoken);
        //Test to see the response is false
        assertFalse(eventResponse.isSuccess());
    }

}
