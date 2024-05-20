package Generate;

import Model.Event;
import Model.Person;
import Model.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CreateGenerations {

    private ArrayList<Person> addedPersons = new ArrayList<>();
    private ArrayList<Event> addedEvents = new ArrayList<>();


    public ArrayList<Person> getAddedPersons() {
        return addedPersons;
    }

    public ArrayList<Event> getAddedEvents() {
        return addedEvents;
    }
    public void generatePerson(User u, int numGenerations, int birthYear) throws FileNotFoundException {

        //Clear the arrays first
        getAddedEvents().clear();
        getAddedPersons().clear();

        Person mother = null;
        Person father = null;

        if (numGenerations > 1) {
            mother = generatePersonHelper(u, numGenerations - 1, "f", birthYear - 30);
            father = generatePersonHelper(u, numGenerations -1, "m", birthYear - 30);

        }
        //Connect the mother and fathers spouse Ids
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());
        //Create their marriage event and make them the same
        RandomEvent randomEvent = new RandomEvent();
        Event wifeMarriage = randomEvent.generateMarriage(u, mother, birthYear - 30);
        RandomID randID = new RandomID();
        String eventID = randID.generateRandomID();
        Event husbandMarriage = new Event(eventID, wifeMarriage.getAssociatedUsername(), father.getPersonID(),
                wifeMarriage.getLatitude(), wifeMarriage.getLongitude(), wifeMarriage.getCountry(),
                wifeMarriage.getCity(), wifeMarriage.getEventType(), wifeMarriage.getYear());
        //add event to the list
        addedEvents.add(wifeMarriage);
        addedEvents.add(husbandMarriage);

        //This is the user that we are passing in who is the descendant of all these generataions
        Person person = new Person(u.getPersonID(), u.getUsername(), u.getFirstName(), u.getLastName(), u.getGender());

        //
        if ((mother != null) && (father != null)) {
            person.setMotherID(mother.getPersonID());
            person.setFatherID(father.getPersonID());
        }
        //Set birth event
        RandomEvent randEvent = new RandomEvent();
        Event birth = randEvent.generateBirth(u,person, birthYear);

        addedEvents.add(birth);
        addedPersons.add(person);
    }

    public Person generatePersonHelper(User u, int numGenerations, String gender, int birthYear) throws FileNotFoundException {

        Person mother = null;
        Person father = null;
        if (numGenerations > 0) {
            //create parents using recursion and
            mother = generatePersonHelper(u, numGenerations - 1, "f", birthYear - 30);
            father = generatePersonHelper(u, numGenerations - 1, "m", birthYear - 30);

            //Connect the mother and fathers spouse Ids
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            //Create their marriage event and make them the same
            RandomEvent randomEvent = new RandomEvent();
            Event wifeMarriage = randomEvent.generateMarriage(u, mother, birthYear - 30);
            RandomID randomID = new RandomID();
            String eventID = randomID.generateRandomID();
            Event husbandMarriage = new Event(eventID, wifeMarriage.getAssociatedUsername(), father.getPersonID(),
                    wifeMarriage.getLatitude(), wifeMarriage.getLongitude(), wifeMarriage.getCountry(),
                    wifeMarriage.getCity(), wifeMarriage.getEventType(), wifeMarriage.getYear());
            //add event to the list
            addedEvents.add(wifeMarriage);
            addedEvents.add(husbandMarriage);
        }
        //THIS IS WHERE THE FIRST PERSON IS CREATED****
        //Create a PERSON FIRST and then EVENTS SECOND
        RandomPerson randomPerson = new RandomPerson();
        RandomEvent randomEvent = new RandomEvent();
        Person newPerson = randomPerson.generatePerson(u, gender);      //create the person
        Event birth = randomEvent.generateBirth(u, newPerson, birthYear);       //Create birth event
        addedEvents.add(birth);
        Event death = randomEvent.generateDeath(u, newPerson, birthYear);       //Create death event
        addedEvents.add(death);

        if ((mother != null) && (father != null)) {
            newPerson.setMotherID(mother.getPersonID());
            newPerson.setFatherID(father.getPersonID());
        }
        //add person to the list
        addedPersons.add(newPerson);

        //Return to the recursive call
        return newPerson;
    }

}
