package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.thre.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.App;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "activity_main";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult");
        switch (resultCode) {
            case RESULT_OK: {
                Log.d("MainActivity", "onActivityResult result ok");
                break;
            }
            case RESULT_CANCELED: {
                Log.d("MainActivity", "onActivityResult result canceled");
                break;
            }
            default: {
                Log.d("MainActivity", "onActivityResult result " + resultCode);
                //
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume - first run: " + App.firstRun);
        if (/*this.preferences.getBoolean("firstRun", true)*/true) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, RESULT_OK);
            return;
        }
        setContentView(R.layout.activity_main);
    }

    private Auftraggeber arbeitgeberdaten;
    private Benutzer benutzerdaten;
    private DatabaseAdapter databaseAdapter;
    private SharedPreferences preferences;
}
