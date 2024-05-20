package kaleb.familyMap.UI;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;
import kaleb.familyMap.AppLogic.DataCache;

public class Lines {

    private List<Integer> visitedEvents = new ArrayList<>();

    public Lines() {

    }

    public LatLng getSpouseEndPoint(Event rootEvent) {
        //get the spouse if there is one
        DataCache dc = DataCache.getInstance();
        Person rootPerson = dc.getPerson(rootEvent.getPersonID());
        if (dc.getPerson(rootPerson.getSpouseID()) == null) {
            return null;
        }
        Person spouse = dc.getPerson(rootPerson.getSpouseID());
        //find spouses birth event if they have one
        int earliestEvent = 5000;
        LatLng endPoint;
        LatLng backUpPoint = null;
        ArrayList<Event> spouseEvents;
        if (dc.getPersonEvents(spouse.getPersonID()) == null) {
            return null;
        }
        spouseEvents =  dc.getPersonEvents(spouse.getPersonID());
        for (Event currEvent : spouseEvents) {
            if (currEvent.getEventType().toLowerCase().equals("birth")) {
                //if they have one then make the end point and return it
                endPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                return endPoint;
            }
            else {
                if (currEvent.getYear() < earliestEvent) {
                    backUpPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                }
            }
        }
        return backUpPoint;
    }

    public LatLng getFatherEndPoint(Event rootEvent) {
        //get the spouse if there is one
        DataCache dc = DataCache.getInstance();
        Person rootPerson = dc.getPerson(rootEvent.getPersonID());
        //if no father then return null
        if (dc.getPerson(rootPerson.getFatherID()) == null) {
            return null;
        }
        Person father = dc.getPerson(rootPerson.getFatherID());
        //find spouses birth event if they have one
        int earliestEvent = 5000;
        LatLng endPoint;
        LatLng backUpPoint = null;
        ArrayList<Event> fatherEvents;
        if (dc.getPersonEvents(father.getPersonID()) == null) {
            return null;
        }
        fatherEvents =  dc.getPersonEvents(father.getPersonID());
        for (Event currEvent : fatherEvents) {
            if (currEvent.getEventType().toLowerCase().equals("birth")) {
                //if they have one then make the end point and return it
                endPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                return endPoint;
            }
            else {
                if (currEvent.getYear() < earliestEvent) {
                    backUpPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                    earliestEvent = currEvent.getYear();
                }
            }
        }
        return backUpPoint;
    }

    public LatLng getMotherEndPoint(Event rootEvent) {
        //get the spouse if there is one
        DataCache dc = DataCache.getInstance();
        Person rootPerson = dc.getPerson(rootEvent.getPersonID());
        //if no mother then return null
        if (dc.getPerson(rootPerson.getMotherID()) == null) {
            return null;
        }
        Person mother = dc.getPerson(rootPerson.getMotherID());
        //find spouses birth event if they have one
        int earliestEvent = 5000;
        LatLng endPoint;
        LatLng backUpPoint = null;
        ArrayList<Event> motherEvents;
        if (dc.getPersonEvents(mother.getPersonID()) == null) {
            return null;
        }
        motherEvents =  dc.getPersonEvents(mother.getPersonID());
        for (Event currEvent : motherEvents) {
            if (currEvent.getEventType().toLowerCase().equals("birth")) {
                //if they have one then make the end point and return it
                endPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                return endPoint;
            }
            else {
                if (currEvent.getYear() < earliestEvent) {
                    backUpPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                    earliestEvent = currEvent.getYear();
                }
            }
        }
        return backUpPoint;
    }

    public LatLng getStoryStartPoints(Event rootEvent, int year) {
        DataCache dc = DataCache.getInstance();
        ArrayList<Event> personEvents = dc.getPersonEvents(rootEvent.getPersonID());
        if (personEvents.isEmpty()) {
            return null;
        }
        for (Event currEvent : personEvents) {
            if (currEvent.getYear() == year) {
                LatLng endPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                return endPoint;
            }
        }

        return null;
    }

    public LatLng getStoryEndPoints(Event rootEvent, int year, List<LatLng> usedEndPoints) {
        DataCache dc = DataCache.getInstance();
        ArrayList<Event> personEvents = dc.getPersonEvents(rootEvent.getPersonID());
        if (personEvents.isEmpty()) {
            return null;
        }
        for (Event currEvent : personEvents) {
            if (currEvent.getYear() == year) {
                LatLng endPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                if (usedEndPoints.contains(endPoint)) {
                    continue;
                }
                return endPoint;

            }
        }

        return null;
    }



}
