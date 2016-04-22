package de.defaultconstructor.mytimestamp.app.android.activities;

import android.util.Log;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.BenutzerdatenFragment;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Person;
import de.defaultconstructor.mytimestamp.app.service.SettingsService;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class SettingsActivity extends MyTimestampActivity implements BenutzerdatenFragment.Callback {

    private static final String TAG = "SettingsActivity";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "on back pressed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");
        if (MyTimestamp.firstRun) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setContentView(R.layout.activity_settings);
        try {
            renderFragment(BenutzerdatenFragment.TAG, R.id.activitySettingsWrapper, true);
        } catch (AndroidException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onSubmit(Benutzer benutzer) {
        this.benutzer = benutzer;
        try {
            if (null != (MyTimestamp.currentBenutzer = this.settingsService.saveBenutzer(this.benutzer))) {
                if (MyTimestamp.firstRun) {
                    MyTimestamp.firstRun = false;
                }
                finish();
            }
        } catch (ServiceException e) {
            Log.e(TAG, "Save failure: " + e);
        }
    }

    private Benutzer benutzer;

    private SettingsService settingsService;

    public SettingsActivity() {
        super();
        this.settingsService = new SettingsService(this);
    }

    public Person getPersonendaten(String tagFragment) {
        Person person;
        switch (tagFragment) {
            case BenutzerdatenFragment.TAG:
                this.benutzer = MyTimestamp.currentBenutzer;
                if (null == this.benutzer) {
                    person = new Benutzer();
                } else {
                    person = this.benutzer;
                }
                break;
            default:
                person = null;
        }
        return person;
    }
}
