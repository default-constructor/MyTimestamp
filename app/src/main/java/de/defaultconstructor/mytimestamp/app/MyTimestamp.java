package de.defaultconstructor.mytimestamp.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.service.AppServiceImpl;

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
        if (null == (MyTimestamp.currentBenutzer = this.applicationService.getCurrentBenutzer())) {
            if (null == (MyTimestamp.currentBenutzer = this.applicationService.persistDummies())) {
                Log.e(TAG, "Dummy konnte nicht gespeichert werden. MyTimestamp wird abgebrochen.");
                System.exit(1);
            }
        }/*
        if (MyTimestamp.firstRun) {
            if (null == (MyTimestamp.currentBenutzer = this.applicationService.persistDummies())) {
                Log.e(TAG, "Dummy konnte nicht gespeichert werden. MyTimestamp wird abgebrochen.");
                System.exit(1);
            }
        } else {
            if (null == (MyTimestamp.currentBenutzer = this.applicationService.getCurrentBenutzer())) {
                Log.e(TAG, "Der aktuelle Benutzer konnte nicht geladen werden. MyTimestamp wird abgebrochen.");
                System.exit(1);
            }
        }*/
        this.aktiveAuftraggeber = this.applicationService.getAktiveAuftraggeber(MyTimestamp.currentBenutzer);
    }

    private AppServiceImpl applicationService;

    private List<Auftraggeber> aktiveAuftraggeber;

    public MyTimestamp() {
        this.applicationService = new AppServiceImpl(this);
    }

    public Auftraggeber getAuftraggeber() {
        return this.aktiveAuftraggeber.get(0);
    }
}
