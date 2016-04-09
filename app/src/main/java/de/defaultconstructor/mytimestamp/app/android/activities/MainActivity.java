package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Intent;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class MainActivity extends MyTimestampActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onResume() {
        super.onResume();
        if (MyTimestamp.firstRun) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, RESULT_OK);
            return;
        }
        if (true) {
            Intent intent = new Intent(this, NewMissionActivity.class);
            startActivityForResult(intent, RESULT_OK);
            return;
        }
        setContentView(R.layout.activity_main);
    }
}
