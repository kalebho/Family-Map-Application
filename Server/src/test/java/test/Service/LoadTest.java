package test.Service;

import DAO.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.LoadResponse;
import Services.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadTest {

    private LoadRequest loadRequest;

    @BeforeEach
    public void setup() throws DataAccessException, FileNotFoundException {


        //Create array of users
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("kalebho", "Laiehawaii", "kalebhoching@gmail.com", "Kaleb", "Ho Ching", "m", "Kaleb_ID");
        User user2 = new User("kahi", "shmall", "kahiedl@gmail.com", "Kahi", "Shmall", "f", "Kahi_ID");
        users.add(user1);
        users.add(user2);
        User userArray [] = users.toArray(new User[0]);
        //Create array of Persons
        ArrayList<Person> persons = new ArrayList<>();
        Person person1 = new Person("Jay_ID", "kalebho", "Jay", "Reid", "f", "George_ID", "Tamra_ID", "Amanda_ID");
        Person person2 = new Person("Koa_ID", "kahi", "Koa", "Eldredge", "m", "Boy_ID", "Val_ID", "None_ID");
        persons.add(person1);
        persons.add(person2);
        Person personArray [] = persons.toArray(new Person[0]);
        //Create array of Events
        ArrayList<Event> events = new ArrayList<>();
        Event event1 = new Event("Play_ID", "kahi", "Koa_ID", 5.0F, 0.0F, "USA", "Honolulu", "Football", 2016);
        Event event2 = new Event("Swim_ID", "kalebho", "Jay_ID", 3.0F, 8.0F, "USA", "Laie", "Batch", 2020);
        events.add(event1);
        events.add(event2);
        Event eventArray [] = events.toArray(new Event[0]);
        //Create load request
        loadRequest = new LoadRequest(userArray, personArray, eventArray);
    }

    @Test
    public void loadPass() throws DataAccessException {
        //Create load service and perform load
        LoadService loadService = new LoadService();
        LoadResponse loadResponse = loadService.load(loadRequest);
        //Check response to see if it was loaded correctlly
        assertTrue(loadResponse.isSuccess());
    }

    @Test
    public void loadFail() throws DataAccessException {
        //empty user array to test fail
        User user [] = new User[0];
        //Clear the user array before passing in the load request
        loadRequest.setUsers(user);
        //Create load service and perform load
        LoadService loadService = new LoadService();
        LoadResponse loadResponse = loadService.load(loadRequest);
        //Check response to see if it was loaded correctlly
        assertFalse(loadResponse.isSuccess());
    }

}
