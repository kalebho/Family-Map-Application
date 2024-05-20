package kaleb.familyMap.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import kaleb.familyMap.R;

public class StartActivity extends AppCompatActivity implements LoginFragment.Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Create
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //Actions
        Fragment fragment = (LoginFragment)fragmentManager.findFragmentById(R.id.fragmentFrameLayout);
        if (fragment == null) {
            fragment = createFirstFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentFrameLayout, fragment)
                    .commit();

        }
        else {
            if (fragment instanceof LoginFragment) {
                ((LoginFragment) fragment).registerListener(this);
            }
        }
    }


    private Fragment createFirstFragment() {
        LoginFragment fragment = new LoginFragment();
        fragment.registerListener(this);
        return fragment;
    }



    public void notifyDone() {
        //Create
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        MapsFragment fragment = new MapsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentFrameLayout, fragment)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.searchMenuItem:
                Intent intent1 = new Intent(StartActivity.this, SearchActivity.class);
                startActivity(intent1);
                return true;
            case R.id.settingsMenuItem:
                Intent intent2 = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
}