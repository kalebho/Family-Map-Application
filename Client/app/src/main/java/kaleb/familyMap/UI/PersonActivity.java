package kaleb.familyMap.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Event;
import Model.Person;
import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.R;

public class PersonActivity extends AppCompatActivity {

    //To receive what was passed in
    public static String personID;
    private TextView firstName;
    private TextView lastName;
    private TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        //To get the person from the data cache
        String thePersonID = intent.getStringExtra(personID);
        DataCache dc = DataCache.getInstance();
        Person person = dc.getPerson(thePersonID);

        //Set the top part of the person activity
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        gender = findViewById(R.id.gender);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if (person.getGender().equals("m")) {
            gender.setText("Male");
        }
        else {
            gender.setText("Female");
        }

        //grab the events for that person and family members
        //get modified list of personEvents
        ArrayList<Event> modifiedEventList = dc.getEventList();
        ArrayList<Event> personEvents = new ArrayList<>();
        for (Event currEvent : modifiedEventList) {
            Person currPerson = dc.getPerson(currEvent.getPersonID());
            if (currPerson.getGender().equals("m") && dc.isMaleSettings()) {
                if (currEvent.getPersonID().equals(thePersonID)) {
                    personEvents.add(currEvent);
                }
            }
            else if (currPerson.getGender().equals("f") && dc.isFemaleSettings()) {
                if (currEvent.getPersonID().equals(thePersonID)) {
                    personEvents.add(currEvent);
                }
            }
        }
        Map<Person, String> personFamily = dc.getPersonFamily(person);

        ExpandableListView expandableListView = findViewById(R.id.expanded_list);

        expandableListView.setAdapter(new ExpandableListAdapter(personEvents, personFamily));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return true;
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private ArrayList<Event> personEvents = new ArrayList<>();
        private Map<Person, String> personFamily = new HashMap<>();

        private static final int EVENTS_GROUP_POSITION = 0;
        private static final int FAMILY_GROUP_POSISTION = 1;

        private ArrayList<Person> the_people = new ArrayList<>();

        ExpandableListAdapter(ArrayList<Event> personEvents, Map<Person, String> personFamily) {
            this.personEvents = personEvents;
            this.personFamily = personFamily;
            personFamilyHelper();
        }

        private void personFamilyHelper() {
            the_people.clear();
            for (Person currPerson : personFamily.keySet()) {
                the_people.add(currPerson);
            }
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    return personEvents.size();
                case FAMILY_GROUP_POSISTION:
                    return personFamily.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group");
            }
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.exapandable_list_group, parent, false);
            }

            TextView groupTitle = convertView.findViewById(R.id.group_title);

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    groupTitle.setText("LIFE EVENTS");
                    break;
                case FAMILY_GROUP_POSISTION:
                    groupTitle.setText("FAMILY");
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group");
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView = getLayoutInflater().inflate(R.layout.search_results, parent, false);

            switch (groupPosition) {
                case EVENTS_GROUP_POSITION:
                    initializeViewEvents(itemView, childPosition);
                    break;
                case FAMILY_GROUP_POSISTION:
                    initializeViewFamily(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group");
            }
            return itemView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }


        private void initializeViewEvents(View view, final int childPosition) {
            TextView topLine = view.findViewById(R.id.search_top);
            TextView bottomLine = view.findViewById(R.id.search_bottom);
            ImageView icon = view.findViewById(R.id.search_result_icon);

            icon.setImageResource(R.mipmap.ic_location_marker);

            Event event = personEvents.get(childPosition);
            String type = event.getEventType();
            String city = event.getCity();
            String country = event.getCountry();
            int year = event.getYear();
            String topString = type + ": " + city + ", " + country + "(" + year + ")";
            topLine.setText(topString);

            DataCache dc = DataCache.getInstance();
            Person person = dc.getPerson(event.getPersonID());
            bottomLine.setText(person.getFirstName() + " " + person.getLastName());

            //Clicking
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra(EventActivity.eventID, event.getEventID());
                    startActivity(intent);
                }
            });

        }

        private void initializeViewFamily(View view, final int childPosition) {
            TextView topLine = view.findViewById(R.id.search_top);
            TextView bottomLine = view.findViewById(R.id.search_bottom);
            ImageView icon = view.findViewById(R.id.search_result_icon);

            Person person = the_people.get(childPosition);
            //Check to see if a father, mother, spouse, or child
            String relation = personFamily.get(person);

            if (person.getGender().equals("m")) {
                icon.setImageResource(R.mipmap.ic_man);
            }
            else {
                icon.setImageResource(R.mipmap.ic_woman);
            }

            topLine.setText(person.getFirstName() + " " + person.getLastName());

            switch (relation) {
                case "m":
                    bottomLine.setText("Mother");
                    break;
                case "d":
                    bottomLine.setText("Father");
                    break;
                case "s":
                    bottomLine.setText("Spouse");
                    break;
                case "c":
                    bottomLine.setText("Child");
                    break;
                default:
                    throw new IllegalArgumentException("noob");
            }

            //Clicking
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    intent.putExtra(PersonActivity.personID, person.getPersonID());
                    startActivity(intent);
                }
            });
        }

    }
}