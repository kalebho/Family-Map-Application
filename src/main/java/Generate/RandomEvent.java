package Generate;

import JSonParser.Location;
import JSonParser.LocationData;
import Model.Event;
import Model.Person;
import Model.User;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class RandomEvent {


    public Event generateBirth(User u, Person p, int year) throws FileNotFoundException {
        RandomID randID = new RandomID();
        String eventID = randID.generateRandomID();
        Location location = getRandomLocation();
        Event e = new Event(eventID, u.getUsername(), p.getPersonID(), location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "birth", year);
        return e;
    }

    public Event generateDeath(User u, Person p, int year) throws FileNotFoundException{
        RandomID randID = new RandomID();
        String eventID = randID.generateRandomID();
        Location location = getRandomLocation();
        Event e = new Event(eventID, u.getUsername(), p.getPersonID(), location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "death", year + 80);
        return e;
    }

    public Event generateMarriage(User u, Person p, int year) throws FileNotFoundException {
        RandomID randID = new RandomID();
        String eventID = randID.generateRandomID();
        Location location = getRandomLocation();
        Event e = new Event(eventID, u.getUsername(), p.getPersonID(), location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "marriage", year + 20);
        return e;
    }


    public Location getRandomLocation() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader reader = new FileReader("json/locations.json");
        LocationData locoData = gson.fromJson(reader, LocationData.class);
        Location [] locationsArray = locoData.getData();
        Random rand = new Random();
        int randomIndex = rand.nextInt(50);
        Location randomLocation = locationsArray[randomIndex];
        return randomLocation;
    }
}
