package com.example.familymap;

import org.junit.*;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.EventsResponse;
import Response.LoginResponse;
import Response.PersonsResponse;
import Response.RegisterResponse;
import kaleb.familyMap.AppLogic.ServerProxy;

public class ServerProxyTest {

    private ServerProxy serverProxy = new ServerProxy();
    private String serverHost = "localhost";
    private String serverPort = "8080";

    @Before
    public void setup() {
        serverProxy.clear(serverHost, serverPort);
    }

    @Test
    public void registerPassTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        Assert.assertEquals(true, registerResponse.isSuccess());
    }

    @Test
    public void registerFailTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        RegisterResponse registerResponse1 = serverProxy.register(registerRequest, serverHost, serverPort);
        Assert.assertEquals(false, registerResponse1.isSuccess());
    }

    @Test
    public void loginPassTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        LoginRequest loginRequest = new LoginRequest("kalebho", "kaleb");
        LoginResponse loginResponse = serverProxy.login(loginRequest, serverHost, serverPort);
        Assert.assertEquals(true, loginResponse.isSuccess());
    }

    @Test
    public void loginFailTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        LoginRequest loginRequest = new LoginRequest("k", "kaleb");
        LoginResponse loginResponse = serverProxy.login(loginRequest, serverHost, serverPort);
        Assert.assertEquals(false, loginResponse.isSuccess());
    }

    @Test
    public void getPeoplePassTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        String auth = registerResponse.getAuthtoken();
        PersonsResponse personsResponse = serverProxy.getPersons(auth, serverHost, serverPort);
        Assert.assertEquals(true, personsResponse.isSuccess());
    }

    @Test
    public void getPeopleFailTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        String auth = registerResponse.getAuthtoken();
        PersonsResponse personsResponse = serverProxy.getPersons("nothing", serverHost, serverPort);
        Assert.assertEquals(false, personsResponse.isSuccess());

    }

    @Test
    public void getEventsPassTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        String auth = registerResponse.getAuthtoken();
        EventsResponse eventsResponse = serverProxy.getEvents(auth, serverHost, serverPort);
        Assert.assertEquals(true, eventsResponse.isSuccess());
    }

    @Test
    public void getEventsFailTest() {
        RegisterRequest registerRequest = new RegisterRequest("kalebho", "kaleb",
                "kalebho@gmail.com", "Kaleb", "Ho Ching", "m");
        RegisterResponse registerResponse = serverProxy.register(registerRequest, serverHost, serverPort);
        String auth = registerResponse.getAuthtoken();
        EventsResponse eventsResponse = serverProxy.getEvents("nothing", serverHost, serverPort);
        Assert.assertEquals(false, eventsResponse.isSuccess());

    }


}
