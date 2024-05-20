package kaleb.familyMap.AppLogic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Model.Event;
import Model.Person;

public class DataCache {


    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public ArrayList<Event> getEventList() {
        if (fatherSettings && !motherSettings) {
           return getFatherEvents(getPerson(userID));
        }
        else if (!fatherSettings && motherSettings) {
            return getMotherEvents(getPerson(userID));
        }
        else if (!fatherSettings && !motherSettings) {
            return getOnlyRootPersonEvents(getPerson(userID));
        }
        else {
            return eventList;
        }
    }

    private ArrayList<Person> personList = new ArrayList<>();
    private Map<String, Person> personMap = new TreeMap<>();
    private ArrayList<Event> eventList = new ArrayList<>();
    private Map<String, Event> eventMap = new TreeMap<>();
    private boolean storySettings = true;
    private boolean treeSettings = true;
    private boolean spouseSettings = true;
    private boolean fatherSettings = true;
    private boolean motherSettings = true;
    private boolean maleSettings = true;
    private String userID;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public boolean isStorySettings() {
        return storySettings;
    }

    public void setStorySettings(boolean storySettings) {
        this.storySettings = storySettings;
    }

    public boolean isTreeSettings() {
        return treeSettings;
    }

    public void setTreeSettings(boolean treeSettings) {
        this.treeSettings = treeSettings;
    }

    public boolean isSpouseSettings() {
        return spouseSettings;
    }

    public void setSpouseSettings(boolean spouseSettings) {
        this.spouseSettings = spouseSettings;
    }

    public boolean isFatherSettings() {
        return fatherSettings;
    }

    public void setFatherSettings(boolean fatherSettings) {
        this.fatherSettings = fatherSettings;
    }

    public boolean isMotherSettings() {
        return motherSettings;
    }

    public void setMotherSettings(boolean motherSettings) {
        this.motherSettings = motherSettings;
    }

    public boolean isMaleSettings() {
        return maleSettings;
    }

    public void setMaleSettings(boolean maleSettings) {
        this.maleSettings = maleSettings;
    }

    public boolean isFemaleSettings() {
        return femaleSettings;
    }

    public void setFemaleSettings(boolean femaleSettings) {
        this.femaleSettings = femaleSettings;
    }

    private boolean femaleSettings = true;
    private Map<String, Boolean> settings = new TreeMap<>();

    private static DataCache instance;

    //Used to get instance
    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    //Only constructor
    private DataCache() {

    }

    //Used to put persons into personsList
    public void insertPersons(Person [] personsArray) {
        for (Person currPerson : personsArray) {
            personMap.put(currPerson.getPersonID(), currPerson);
            personList.add(currPerson);
        }
    }

    //Used to put events into eventsList
    public void insertEvents(Event [] eventsArray) {
        for (Event currEvent : eventsArray) {
            eventMap.put(currEvent.getEventID(), currEvent);
            eventList.add(currEvent);
        }
    }

    public Person getPerson(String personID) {
        if (personID == null) {
            return null;
        }
        return personMap.get(personID);
    }

    public Event getEvent(String eventID) {
        return eventMap.get(eventID);
    }

    public ArrayList<Event> getPersonEvents(String personID) {
        ArrayList<Event> events = new ArrayList<>();
//        ArrayList<Event> modifiedEventList = getEventList();
        for (Event currEvent : eventList) {
            Person currPerson = getPerson(currEvent.getPersonID());
            if (currPerson.getGender().equals("m") && maleSettings) {
                if (currEvent.getPersonID().equals(personID)) {
                    events.add(currEvent);
                }
            }
            else if (currPerson.getGender().equals("f") && femaleSettings) {
                if (currEvent.getPersonID().equals(personID)) {
                    events.add(currEvent);
                }
            }
        }
        return events;
    }

    public Map<Person, String> getPersonFamily(Person person) {
        String personID = person.getPersonID();
        String motherID = person.getMotherID();
        String fatherID = person.getFatherID();
        String spouseID = person.getSpouseID();
        Map<Person, String> family = new HashMap<>();
        for (Person currPerson : personList) {
            String mom = "";
            String dad = "";
            String spouse = "";
            String child = "";
            if (currPerson.getPersonID().equals(motherID)) {
                mom = "m";
                family.put(currPerson, mom);
            }
            if (currPerson.getPersonID().equals(fatherID)) {
                dad = "d";
                family.put(currPerson, dad);
            }
            if (currPerson.getPersonID().equals(spouseID)) {
                spouse = "s";
                family.put(currPerson, spouse);
            }
            if (personID.equals(currPerson.getFatherID())) {
                child = "c";
                family.put(currPerson, child);
            }
            else if (personID.equals(currPerson.getMotherID())) {
                child = "c";
                family.put(currPerson, child);
            }
        }
        return family;
    }

    public ArrayList<Event> getFatherEvents(Person rootPerson) {
        ArrayList<Event> events = new ArrayList<>();
        //get the root persons event and events for their spouse
        ArrayList<Event> rootPersonEvents = getPersonEvents(rootPerson.getPersonID());
        ArrayList<Event> rootPersonSpouseEvents = getPersonEvents(rootPerson.getSpouseID());
        if (rootPersonEvents != null) {
            for (Event event : rootPersonEvents) {
                events.add(event);
            }
        }
        if (rootPersonSpouseEvents != null) {
            for (Event event : rootPersonSpouseEvents) {
                events.add(event);
            }
        }
        //get just the events of the father
        Person father = getPerson(rootPerson.getFatherID());
        ArrayList<Event> fatherEvents = getPersonEvents(father.getPersonID());
        if (fatherEvents == null) {
            //do nothing
        }
        else {
            for (Event event : fatherEvents) {
                events.add(event);
            }
        }
        getEventsHelper(father, events);
        return events;
    }

    private void getEventsHelper(Person rootPerson, ArrayList<Event> events) {
        if (rootPerson == null) {
            //finish
        }
        else {
            Person father = null;
            if (getPerson(rootPerson.getFatherID()) != null) {
                father = getPerson(rootPerson.getFatherID());
                if (getPersonEvents(father.getPersonID()) != null) {
                    ArrayList<Event> fatherEvents = getPersonEvents(father.getPersonID());
                    if (fatherEvents == null) {
                        //do nothing
                    }
                    else {
                        for (Event event : fatherEvents) {
                            events.add(event);
                        }
                    }
                }
            }

            Person mother = null;
            if (getPerson(rootPerson.getMotherID()) != null) {
                mother = getPerson(rootPerson.getMotherID());
                if (getPersonEvents(mother.getPersonID()) != null) {
                    ArrayList<Event> motherEvents = getPersonEvents(mother.getPersonID());

                    if (motherEvents == null) {
                        //do nothing
                    }
                    else {
                        for (Event event : motherEvents) {
                            events.add(event);
                        }
                    }
                }
            }
            //recurse down the line
            getEventsHelper(father, events);
            getEventsHelper(mother, events);
        }
    }





    public ArrayList<Event> getMotherEvents(Person rootPerson) {
        ArrayList<Event> events = new ArrayList<>();
        //get the root persons event and events for their spouse
        ArrayList<Event> rootPersonEvents = getPersonEvents(rootPerson.getPersonID());
        ArrayList<Event> rootPersonSpouseEvents = getPersonEvents(rootPerson.getSpouseID());
        if (rootPersonEvents != null) {
            for (Event event : rootPersonEvents) {
                events.add(event);
            }
        }
        if (rootPersonSpouseEvents != null) {
            for (Event event : rootPersonSpouseEvents) {
                events.add(event);
            }
        }
        //get just the events of the mother
        Person mother = getPerson(rootPerson.getMotherID());
        ArrayList<Event> motherEvents = getPersonEvents(mother.getPersonID());
        if (motherEvents == null) {
            //do nothing
        }
        else {
            for (Event event : motherEvents) {
                events.add(event);
            }
        }
        getEventsHelper(mother, events);
        return events;
    }

    public ArrayList<Event> getOnlyRootPersonEvents(Person rootPerson) {
        ArrayList<Event> events = new ArrayList<>();
        //get the root persons event and events for their spouse
        ArrayList<Event> rootPersonEvents = getPersonEvents(rootPerson.getPersonID());
        ArrayList<Event> rootPersonSpouseEvents = getPersonEvents(rootPerson.getSpouseID());
        if (rootPersonEvents != null) {
            for (Event event : rootPersonEvents) {
                events.add(event);
            }
        }
        if (rootPersonSpouseEvents != null) {
            for (Event event : rootPersonSpouseEvents) {
                events.add(event);
            }
        }
        return events;
    }







    public void clearCache() {
        personList.clear();
        eventList.clear();
        personMap.clear();
        eventMap.clear();
    }

}


