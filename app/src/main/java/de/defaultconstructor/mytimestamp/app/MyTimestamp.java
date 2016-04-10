package de.defaultconstructor.mytimestamp.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.service.MyTimestampService;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class MyTimestamp extends Application {

    public static final String TAG = "MyTimestamp";

    /** Haelt den aktuellen Benutzer der MyTimestamp. */
    public static Benutzer currentBenutzer;

    /** Haelt den Status der MyTimestamp, ob es sich um den ersten Aufruf handelt. */
    public static boolean firstRun = true;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("preferenceName", MODE_PRIVATE);
        MyTimestamp.firstRun = sharedPreferences.getBoolean("firstRun", true) && MyTimestamp.firstRun;
        if (null != (MyTimestamp.currentBenutzer = this.myTimestampService.getCurrentBenutzer())) {
            Log.d(TAG, "current benutzer " + MyTimestamp.currentBenutzer.toString());
            MyTimestamp.firstRun = false;
            return;
        }
        Log.d(TAG, "current benutzer is null");
    }

    private MyTimestampService myTimestampService;

    public MyTimestamp() {
        this.myTimestampService = new MyTimestampService(this);
    }
}
