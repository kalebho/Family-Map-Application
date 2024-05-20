package kaleb.familyMap.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import kaleb.familyMap.AppLogic.DataCache;
import kaleb.familyMap.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch storySwitch;
    private Switch treeSwitch;
    private Switch spouseSwitch;
    private Switch fathersSwitch;
    private Switch mothersSwitch;
    private Switch maleSwitch;
    private Switch femaleSwitch;
    private LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storySwitch = findViewById(R.id.story_switch);
        treeSwitch = findViewById(R.id.family_tree_switch);
        spouseSwitch = findViewById(R.id.spouse_switch);
        fathersSwitch = findViewById(R.id.fathers_side_switch);
        mothersSwitch = findViewById(R.id.mothers_side_switch);
        maleSwitch = findViewById(R.id.male_switch);
        femaleSwitch = findViewById(R.id.female_switch);
        logout = findViewById(R.id.logout);

        DataCache dc = DataCache.getInstance();

        storySwitch.setChecked(dc.isStorySettings());
        treeSwitch.setChecked(dc.isTreeSettings());
        spouseSwitch.setChecked(dc.isSpouseSettings());
        fathersSwitch.setChecked(dc.isFatherSettings());
        mothersSwitch.setChecked(dc.isMotherSettings());
        maleSwitch.setChecked(dc.isMaleSettings());
        femaleSwitch.setChecked(dc.isFemaleSettings());





        storySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setStorySettings(true);
                } else {
                    dc.setStorySettings(false);
                }
            }
        });
        treeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setTreeSettings(true);
                } else {
                    dc.setTreeSettings(false);
                }
            }
        });
        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setSpouseSettings(true);
                } else {
                    dc.setSpouseSettings(false);
                }
            }
        });
        fathersSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setFatherSettings(true);
                } else {
                    dc.setFatherSettings(false);
                }
            }
        });
        mothersSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setMotherSettings(true);
                } else {
                    dc.setMotherSettings(false);
                }
            }
        });
        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setMaleSettings(true);
                } else {
                    dc.setMaleSettings(false);
                }
            }
        });
        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dc.setFemaleSettings(true);
                } else {
                    dc.setFemaleSettings(false);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

}

