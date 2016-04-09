package de.defaultconstructor.mytimestamp.app.android.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.MyTimestampFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.NeuerAuftragFragment;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Person;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionActivity extends MyTimestampActivity implements MyTimestampFragment.FragmentListener {

    public static final String TAG = "NewMissionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        setContentView(R.layout.activity_newmission);
        renderFragment(NeuerAuftragFragment.TAG, R.id.activityNewMissionWrapper);
    }

    @Override
    public void onSubmit(Person person) throws AppException {
        Log.d(TAG, "on submit");
        finish();
    }

    public NewMissionActivity() {
        super();
        Log.d(TAG, "New mission activity");
    }
}
