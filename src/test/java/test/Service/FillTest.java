package test.Service;

import DAO.DataAccessException;
import Request.RegisterRequest;
import Response.ClearResponse;
import Response.FillResponse;
import Response.RegisterResponse;
import Services.ClearService;
import Services.FillService;
import Services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class FillTest {

    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException {

        //clear out the current data
        ClearService clearService = new ClearService();
        ClearResponse clearResponse = clearService.clear();

        //Register a person in the database
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse  = registerService.register(registerRequest);
    }

    @Test
    public void fillPass() throws DataAccessException, FileNotFoundException {
        //Create fill service and fill with 3 generations
        FillService fillService = new FillService();
        FillResponse fillResponse = fillService.fill("kalebho", 3);
        //Test to see the response is true
        assertTrue(fillResponse.isSuccess());
    }

    @Test
    public void fillFail() throws DataAccessException, FileNotFoundException {
        //Create fill service and try to fill with a username that doesn't exist
        FillService fillService = new FillService();
        FillResponse fillResponse = fillService.fill("rajah", 3);
        //Test to see the response is true
        assertFalse(fillResponse.isSuccess());
    }
}
