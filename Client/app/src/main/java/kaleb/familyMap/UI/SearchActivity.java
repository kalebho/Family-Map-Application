package kaleb.familyMap.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;
import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.R;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));


        DataCache dc = DataCache.getInstance();
        ArrayList<Person> listOfPersons = dc.getPersonList();
        ArrayList<Event> listOfEvents = dc.getEventList();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                SettingsAdapter adapter = new SettingsAdapter(listOfPersons, listOfEvents);
                adapter.filter(newText);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

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


    private class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder> {

        private ArrayList<Person> listOfPersons;
        private ArrayList<Event> listOfEvents;
        private ArrayList<Person> filteredPersons = new ArrayList<>();
        private ArrayList<Event> filteredEvents = new ArrayList<>();


        SettingsAdapter(ArrayList<Person> listOfPersons, ArrayList<Event> listOfEvents) {
            this.listOfPersons = listOfPersons;
            this.listOfEvents = listOfEvents;
        }

        @NonNull
        @Override
        public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.search_results, parent, false);
            return new SettingsViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
            if (position < filteredPersons.size()) {
                holder.bind(filteredPersons.get(position));
            }
            else{
                holder.bind(filteredEvents.get(position - filteredPersons.size()));
            }
        }

        @Override
        public int getItemCount() {
            return filteredPersons.size() + filteredEvents.size();
        }

        public void filter(String query) {
            DataCache dc = DataCache.getInstance();
            filteredEvents.clear();
            filteredPersons.clear();
            if (!query.isEmpty()) {
                for (Person currPerson : listOfPersons) {
                    if ((currPerson.getFirstName().toLowerCase().contains(query)) || (currPerson.getLastName().toLowerCase().contains(query))) {
                        filteredPersons.add(currPerson);
                    }
                }
                for (Event currEvent : listOfEvents) {
                    String yearString = String.valueOf(currEvent.getYear());
                    Person associatedPerson = dc.getPerson(currEvent.getPersonID());
                    if (associatedPerson.getGender().equals("m") && (dc.isMaleSettings())) {
                        if ((currEvent.getCity().toLowerCase().contains(query)) || (currEvent.getCountry().toLowerCase().contains(query)) || (yearString.toLowerCase().contains(query) || (currEvent.getEventType().toLowerCase().contains(query))) ) {
                            filteredEvents.add(currEvent);
                        }
                    }
                    else if (associatedPerson.getGender().equals("f") && (dc.isFemaleSettings())) {
                        if ((currEvent.getCity().toLowerCase().contains(query)) || (currEvent.getCountry().toLowerCase().contains(query)) || (yearString.toLowerCase().contains(query) || (currEvent.getEventType().toLowerCase().contains(query))) ) {
                            filteredEvents.add(currEvent);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }




    }

    private class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView top;
        private final TextView bottom;
        private final ImageView icon;
        private final int viewType;
        private Person person = null;
        private Event event = null;
        SettingsViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            top = itemView.findViewById(R.id.search_top);
            bottom = itemView.findViewById(R.id.search_bottom);
            icon = itemView.findViewById(R.id.search_result_icon);

            itemView.setOnClickListener(this);

        }

        private void bind(Person person) {
            this.person = person;
            if (person.getGender().equals("m")) {
                icon.setImageResource(R.mipmap.ic_man);
            }
            else {
                icon.setImageResource(R.mipmap.ic_woman);
            }
            top.setText(person.getFirstName() + " " + person.getLastName());
            bottom.setText("");
        }

        private void bind(Event event) {
            this.event = event;
            icon.setImageResource(R.mipmap.ic_location_marker);
            top.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + "(" + event.getYear() + ")");
            DataCache dc = DataCache.getInstance();
            Person person1 = dc.getPerson(event.getPersonID());
            bottom.setText(person1.getFirstName() + " " + person1.getLastName());
        }




        @Override
        public void onClick(View view) {
            TextView bottom = view.findViewById(R.id.search_bottom);
            TextView top = view.findViewById(R.id.search_top);
            String bottomText = bottom.getText().toString();
            if (bottomText.isEmpty()) {
                Intent intent1 = new Intent(SearchActivity.this, PersonActivity.class);
                intent1.putExtra(PersonActivity.personID, person.getPersonID());
                startActivity(intent1);
            }
            else {
                Intent intent2 = new Intent(SearchActivity.this, EventActivity.class);
                intent2.putExtra(EventActivity.eventID, event.getEventID());
                startActivity(intent2);
            }
        }
    }



}