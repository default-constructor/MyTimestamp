package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyTimestamp.firstRun) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, RESULT_OK);
            return;
        }
        setContentView(R.layout.activity_main);
    }
}
