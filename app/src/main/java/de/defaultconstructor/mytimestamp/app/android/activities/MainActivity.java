package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyTimestamp.firstRun) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, RESULT_OK);
            return;
        }
        if (false) {
            Intent intent = new Intent(this, NewMissionActivity.class);
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
        if (null != auftragList) { // FIXME wieder ausbauen
            for (Auftrag auftrag : auftragList) {
                Log.d(TAG, auftrag.toString());
            }
        }
        return auftragList;
    }

    public List<Auftrag> getAbgeschlosseneAuftraege() {
        List<Auftrag> auftragList = null;
        try {
            auftragList = this.mainService.loadAbgeschlosseneAuftraege();
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
        if (null != auftragList) { // FIXME wieder ausbauen
            for (Auftrag auftrag : auftragList) {
                Log.d(TAG, auftrag.toString());
            }
        }
        return auftragList;
    }

    public List<Auftrag> getAktuelleAuftraege() {
        List<Auftrag> auftragList = null;
        try {
            auftragList = this.mainService.loadAktuelleAuftraege();
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
        if (null != auftragList) { // FIXME wieder ausbauen
            for (Auftrag auftrag : auftragList) {
                Log.d(TAG, auftrag.toString());
            }
        }
        return auftragList;
    }

    public List<Auftrag> getAnstehendeAuftraege() {
        List<Auftrag> auftragList = null;
        try {
            auftragList = this.mainService.loadAnstehendeAuftraege();
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
        if (null != auftragList) { // FIXME wieder ausbauen
            for (Auftrag auftrag : auftragList) {
                Log.d(TAG, auftrag.toString());
            }
        }
        return auftragList;
    }
}
