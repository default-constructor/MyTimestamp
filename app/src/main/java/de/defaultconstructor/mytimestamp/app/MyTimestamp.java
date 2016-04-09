package de.defaultconstructor.mytimestamp.app;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.List;

import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
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
        if (null != (MyTimestamp.currentBenutzer = this.applicationService.getCurrentBenutzer())) {/*
            if (null == (MyTimestamp.currentBenutzer = this.applicationService.persistDummies())) {
                Log.e(TAG, "Dummy konnte nicht gespeichert werden. MyTimestamp wird abgebrochen.");
                System.exit(1);
            }*/
            MyTimestamp.firstRun = false;
        }
    //    this.aktiveAuftraggeber = this.applicationService.getAktiveAuftraggeber(MyTimestamp.currentBenutzer);
    }

    private MyTimestampService applicationService;

    private List<Auftraggeber> aktiveAuftraggeber;

    public MyTimestamp() {
        this.applicationService = new MyTimestampService(this);
    }

    public Auftraggeber getAuftraggeber() {
        return this.aktiveAuftraggeber.get(0);
    }
}
