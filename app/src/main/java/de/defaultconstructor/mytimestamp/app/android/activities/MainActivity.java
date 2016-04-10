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
        Log.d(TAG, "on create view");
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");
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

    public List<Auftrag> getAuftragList() {
        return this.mainService.loadAuftragList();
    }

    public MainActivity() {
        super();
        this.mainService = new MainService(this);
    }
}
