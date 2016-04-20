package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.MainFragment;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.service.MainService;

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
        setContentView(R.layout.activity_main);
        try {
            renderFragment(MainFragment.TAG, R.id.activityMainWrapper, true);
        } catch (AndroidException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private MainService mainService;

    public MainActivity() {
        super();
        this.mainService = new MainService(this);
    }

    public List<Auftrag> getAuftragList() {
        List<Auftrag> auftragList = null;
        try {
            auftragList = this.mainService.loadAuftragList();
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
        return auftragList;
    }
}
