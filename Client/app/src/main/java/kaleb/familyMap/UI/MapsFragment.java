package kaleb.familyMap.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import Model.Event;
import Model.Person;
import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback, OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private Map<String, Float> eventTypes = new TreeMap<>();
    private TextView eventText;
    private ImageView genderIcon;
    private LinearLayout eventDisplay;
    private Event passedEvent;
    private Polyline spouseLine;
    private List<Polyline> treeLines = new ArrayList<>();
    private List<Polyline> storyLines = new ArrayList<>();
    private List<LatLng> usedStoryEndPoints = new ArrayList<>();
//    private ArrayList<Marker> maleMarkers = new ArrayList<>();
//    private ArrayList<Marker> femaleMarkers = new ArrayList<>();
    private ArrayList<Event> maleEvents = new ArrayList<>();
    private ArrayList<Event> femaleEvents = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        eventText = view.findViewById(R.id.event_text);
        genderIcon = view.findViewById(R.id.gender_icon);
        eventDisplay = view.findViewById(R.id.event_display);



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    public void passEvent(Event event) {
        passedEvent = event;
    }


    @Override
    public void onResume() {
        super.onResume();


        if (map != null) {

            map.clear();
            setMapEvents(map);

            DataCache dc = DataCache.getInstance();
            if (!dc.isStorySettings() && (storyLines != null)) {
                for (Polyline line : storyLines) {
                    line.remove();
                }
            }
            if (!dc.isTreeSettings() && (treeLines != null)) {
                for (Polyline line : treeLines) {
                    line.remove();
                }
            }
            if (!dc.isSpouseSettings() && (spouseLine != null)) {
                spouseLine.remove();
            }
//            if (dc.isMaleSettings()) {
//                putMaleMarkers();
//            }
//            if (dc.isFemaleSettings()) {
//                putFemaleMarkers();
//            }
        }

    }

//    private void removeMaleMarkers() {
//        for (Marker marker : maleMarkers) {
//            marker.remove();
//        }
//    }
//
//
//    private void removeFemaleMarkers() {
//        for (Marker marker : femaleMarkers) {
//            marker.remove();
//        }
//    }

    private void putMaleMarkers() {
        float color = 0;
        for (Event currEvent : maleEvents) {
            //Get event type to specify color
            if (eventTypes.get(currEvent.getEventType()) == null) {
                eventTypes.put(currEvent.getEventType().toLowerCase(), generateRandomColor());
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            else {
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(color)));
            marker.setTag(currEvent);
        }
    }

    private void putFemaleMarkers() {
        float color = 0;
        for (Event currEvent : femaleEvents) {
            //Get event type to specify color
            if (eventTypes.get(currEvent.getEventType().toLowerCase()) == null) {
                eventTypes.put(currEvent.getEventType().toLowerCase(), generateRandomColor());
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            else {
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(color)));
            marker.setTag(currEvent);
        }
    }





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback((OnMapLoadedCallback) this);
        //place all the markers on the map
        setMapEvents(map);
        //set the event text below if there is an event passed in

        if (passedEvent != null){
            //set the camera to be on the event
            map.setOnMapLoadedCallback((OnMapLoadedCallback) this);
            LatLng passedLocation = new LatLng(passedEvent.getLatitude(), passedEvent.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(passedLocation));
            //set the event text below
            DataCache dc = DataCache.getInstance();
            Person person = dc.getPerson(passedEvent.getPersonID());
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String type = passedEvent.getEventType();
            String city = passedEvent.getCity();
            String country = passedEvent.getCountry();
            int year = passedEvent.getYear();

            String result = firstName + " " + lastName + "\n" + type + ": " + city + ", " + country + "(" + year + ")";
            eventText.setText(result);

            if (person.getGender().equals("m")) {
                genderIcon.setImageResource(R.mipmap.ic_man);
            }
            else {
                genderIcon.setImageResource(R.mipmap.ic_woman);
            }

            //Draw the lines for the spouse
            if (dc.isSpouseSettings()) {
                drawSpouseLines(passedEvent);
            }
            //Draw the lines for the family
            if (dc.isTreeSettings()) {
                drawLine(passedEvent);
            }
            //Draw the lines for the story
            if (dc.isStorySettings()) {
                drawStoryLines(passedEvent);
            }
        }


        //Make the inital event text a clicker
        eventDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PersonActivity.class);
                intent.putExtra(PersonActivity.personID, passedEvent.getPersonID());
                startActivity(intent);
            }
        });
        //set the map to be a click listener
        map.setOnMarkerClickListener(this);
    }

    //Function to start the map with all the events from the database
    private void setMapEvents(GoogleMap map) {
        eventTypes.clear();
        float color = 0;
        DataCache dc = DataCache.getInstance();
        ArrayList<Event> events = dc.getEventList();
        for (Event currEvent : events) {
            //Get event type to specify color
            if (eventTypes.get(currEvent.getEventType().toLowerCase()) == null) {
                eventTypes.put(currEvent.getEventType().toLowerCase(), generateRandomColor());
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            else {
                color = eventTypes.get(currEvent.getEventType().toLowerCase());
            }
            Person associatedPerson = dc.getPerson(currEvent.getPersonID());

            if (associatedPerson.getGender().equals("m") && dc.isMaleSettings()) {
                LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(color)));
                maleEvents.add(currEvent);
                marker.setTag(currEvent);
            }
            else if (associatedPerson.getGender().equals("f") && dc.isFemaleSettings()) {
                LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(color)));
                femaleEvents.add(currEvent);
                marker.setTag(currEvent);
            }
//            else {
//                LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
//                Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(color)));
//                femaleEvents.add(currEvent);
//                marker.setTag(currEvent);
//            }

        }

    }

    private float generateRandomColor() {
        Random random = new Random();
        float[] options = {210f, 240f, 180f, 120f, 300f, 30f, 0f, 330f, 270f, 60f};
        float color = options[random.nextInt(options.length)];
        return color;
    }


    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (passedEvent == null) {
            inflater.inflate(R.menu.menu, menu);
        }
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Event event = (Event) marker.getTag();
        DataCache dc = DataCache.getInstance();
        Person person = dc.getPerson(event.getPersonID());
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String type = event.getEventType();
        String city = event.getCity();
        String country = event.getCountry();
        int year = event.getYear();

        String result = firstName + " " + lastName + "\n" + type + ": " + city + ", " + country + "(" + year + ")";
        eventText.setText(result);

        if (person.getGender().equals("m")) {
            genderIcon.setImageResource(R.mipmap.ic_man);
        }
        else {
            genderIcon.setImageResource(R.mipmap.ic_woman);
        }

        //Remove lines for spouse
        if (spouseLine != null) {
            spouseLine.remove();
        }
        //Draw the lines for the spouse
        if (dc.isSpouseSettings()) {
            drawSpouseLines(event);
        }

        //Remove lines for tree
        if (!treeLines.isEmpty()) {
            for (Polyline currLine : treeLines) {
                currLine.remove();
            }
        }
        //Draw the lines for family tree
        if (dc.isTreeSettings()) {
            drawLine(event);
        }

        //Remove lines for story
        if (!storyLines.isEmpty()) {
            for (Polyline currLine : storyLines) {
                currLine.remove();
            }
        }
        //Draw Story lines
        if (dc.isStorySettings()) {
            drawStoryLines(event);
        }



        eventDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PersonActivity.class);
                intent.putExtra(PersonActivity.personID, person.getPersonID());
                startActivity(intent);
            }
        });

        return true;

    }

    private void drawSpouseLines(Event rootEvent) {
        Lines lines = new Lines();
        LatLng startPoint = new LatLng(rootEvent.getLatitude(), rootEvent.getLongitude());
        //if there is no spouse then return
        LatLng endPoint = lines.getSpouseEndPoint(rootEvent);
        if (endPoint == null) {
            return;
        }

        //specify line options
        PolylineOptions options = new PolylineOptions()
                .add(startPoint)
                .add(endPoint)
                .color((int) Color.GREEN)
                .width(10);
        Polyline line = map.addPolyline(options);
        spouseLine = line;
    }

    private void drawLine(Event rootEvent) {
        DataCache dc = DataCache.getInstance();
        Lines lines = new Lines();
        LatLng startPoint = new LatLng(rootEvent.getLatitude(), rootEvent.getLongitude());
        //Go down the father side
        LatLng fatherEndPoint = lines.getFatherEndPoint(rootEvent);
        if (fatherEndPoint == null) {
            //dont do anything
        }
        else if (dc.isFatherSettings()) {
            //Draw the line to the next person before recursing
            //specify line options
            int color = (int) Color.BLUE;
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(fatherEndPoint)
                    .color(color)
                    .width(10);
            Polyline line = map.addPolyline(options);
            treeLines.add(line);

            //get next father event and set as root event
            Event fatherEvent = getFatherEvent(rootEvent);
            drawLineHelper(fatherEvent, 10 - 3, color);
        }


        //Go down the mother side
        LatLng motherEndPoint = lines.getMotherEndPoint(rootEvent);
        if (motherEndPoint == null) {
            //dont do anything
        }
        else if (dc.isMotherSettings()) {

            //Draw the line to the next person before recursing
            //specify line options
            int color = (int) Color.BLUE;
            PolylineOptions optionsM = new PolylineOptions()
                    .add(startPoint)
                    .add(motherEndPoint)
                    .color(color)
                    .width(10);
            Polyline line = map.addPolyline(optionsM);
            //save the line to delete later
            treeLines.add(line);

            //get next mother event and set as root event
            Event motherEvent = getMotherEvent(rootEvent);
            drawLineHelper(motherEvent, 10 - 3, color);
        }
    }

    private void drawLineHelper(Event rootEvent, float width, int color) {
        Lines lines = new Lines();
        LatLng startPoint = new LatLng(rootEvent.getLatitude(), rootEvent.getLongitude());
        LatLng fatherEndPoint = lines.getFatherEndPoint(rootEvent);
        if (fatherEndPoint == null){
            //do nothing
        }
        else {
            //Draw the line to the next person before recursing
            //specify line options
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(fatherEndPoint)
                    .color(color)
                    .width(width);
            Polyline line = map.addPolyline(options);
            treeLines.add(line);

            //get next father event and set as root event
            Event fatherEvent = getFatherEvent(rootEvent);
            drawLineHelper(fatherEvent, width - 3, color);
        }

        LatLng motherEndPoint = lines.getMotherEndPoint(rootEvent);
        if (motherEndPoint == null) {
            //do nothing
        }
        else {
            //Draw the line to the next person before recursing
            //specify line options
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(motherEndPoint)
                    .color(color)
                    .width(width);
            Polyline line = map.addPolyline(options);
            treeLines.add(line);

            //get next father event and set as root event
            Event motherEvent = getMotherEvent(rootEvent);
            drawLineHelper(motherEvent, width - 3, color);
        }


    }

    private void drawStoryLines(Event rootEvent) {
        Lines lines = new Lines();
        int color = (int) Color.RED;
        //get a ordered set of years of events
        LatLng startPoint = null;
        LatLng endPoint = null;
        usedStoryEndPoints = new ArrayList<>();
        ArrayList<Integer> orderedYears = getOrderedEventYears(rootEvent);
        for (int i = 0; i < orderedYears.size() - 1; i++) {
            startPoint = lines.getStoryStartPoints(rootEvent, orderedYears.get(i));
            endPoint = lines.getStoryEndPoints(rootEvent, orderedYears.get(i + 1), usedStoryEndPoints);

            if (endPoint == null) {
                return;
            }
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(color)
                    .width(10);
            Polyline line = map.addPolyline(options);
            usedStoryEndPoints.add(endPoint);
            storyLines.add(line);

        }
    }


    private Event getFatherEvent(Event event) {
        DataCache dc = DataCache.getInstance();
        Person rootPerson = dc.getPerson(event.getPersonID());
        //if no father then return null
        Person father = dc.getPerson(rootPerson.getFatherID());
        ArrayList<Event> fatherEvents;
        int earliestEvent = 5000;
        Event backupEvent = null;
        fatherEvents = dc.getPersonEvents(father.getPersonID());
        for (Event currEvent : fatherEvents) {
            if (currEvent.getEventType().toLowerCase().equals("birth")) {
                return currEvent;
            }
            else {
                if (currEvent.getYear() < earliestEvent) {
                    backupEvent = currEvent;
                }
            }
        }
        return backupEvent;
    }

    private Event getMotherEvent(Event event) {
        DataCache dc = DataCache.getInstance();
        Person rootPerson = dc.getPerson(event.getPersonID());
        Person mother = dc.getPerson(rootPerson.getMotherID());
        ArrayList<Event> motherEvents;
        int earliestEvent = 5000;
        Event backupEvent = null;
        motherEvents = dc.getPersonEvents(mother.getPersonID());
        for (Event currEvent : motherEvents) {
            if (currEvent.getEventType().toLowerCase().equals("birth")) {
                return currEvent;
            }
            else {
                if (currEvent.getYear() < earliestEvent) {
                    backupEvent = currEvent;
                }
            }
        }
        return backupEvent;
    }

//    private Event getFirstEvent(Event event) {
//        DataCache dc = DataCache.getInstance();
//        ArrayList<Event> personEvents = dc.getPersonEvents(event.getPersonID());
//        if (personEvents == null) {
//            return null;
//        }
//        int earliest = 5000;
//        Event firstEvent = null;
//        for (Event currEvent : personEvents) {
//            if (currEvent.getYear() < earliest) {
//                firstEvent = currEvent;
//            }
//        }
//        return firstEvent;
//    }

    private ArrayList<Integer> getOrderedEventYears(Event event) {
        ArrayList<Integer> orderedEventsYears = new ArrayList<>();
        DataCache dc = DataCache.getInstance();
        ArrayList<Event> personEvents = dc.getPersonEvents(event.getPersonID());
        if (personEvents == null) {
            return null;
        }
        for (Event currEvent : personEvents) {
            orderedEventsYears.add(currEvent.getYear());
        }
        Collections.sort(orderedEventsYears);
        return orderedEventsYears;
    }









}