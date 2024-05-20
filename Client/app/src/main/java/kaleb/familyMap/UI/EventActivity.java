package kaleb.familyMap.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import Model.Event;
import Model.Person;
import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.R;

public class EventActivity extends AppCompatActivity {

    public static String eventID;
    private TextView eventText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        //To get the person from the data cache
        String theEventID = intent.getStringExtra(eventID);
        DataCache dc = DataCache.getInstance();
        Event event = dc.getEvent(theEventID);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        MapsFragment fragment = (MapsFragment)fragmentManager.findFragmentById(R.id.map_fragment_layout);
        if (fragment == null) {
            fragment = createFirstFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.map_fragment_layout, fragment)
                    .commit();

        }
        fragment.passEvent(event);

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


    private MapsFragment createFirstFragment() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }
}