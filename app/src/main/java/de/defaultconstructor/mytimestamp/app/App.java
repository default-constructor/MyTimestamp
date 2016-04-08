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
public class App extends Application {

    public static final String TAG = "App";

    /** Haelt den aktuellen Benutzer der App. */
    public static Benutzer currentBenutzer;

    /** Haelt den Status der App, ob es sich um den ersten Aufruf handelt. */
    public static boolean firstRun = true;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("preferenceName", MODE_PRIVATE);
        App.firstRun = sharedPreferences.getBoolean("firstRun", true) && App.firstRun;

        Log.d(TAG, "first run: " + App.firstRun);

        if (App.firstRun) {
            if (null == (App.currentBenutzer = this.applicationService.persistDummies())) {
                Log.e(TAG, "Kein Dummy-Benutzer. Die App wird abgebrochen.");
                System.exit(1);
            }
        } else {
            if (null == (App.currentBenutzer = this.applicationService.getCurrentBenutzer())) {
                Log.e(TAG, "Der aktuelle Benutzer konnte nicht geladen werden. Die App wird abgebrochen.");
                System.exit(1);
            }
        }
        this.aktiveAuftraggeber = this.applicationService.getAktiveAuftraggeber(App.currentBenutzer);
    }

    private AppServiceImpl applicationService;

    private List<Auftraggeber> aktiveAuftraggeber;

    private SharedPreferences sharedPreferences;

    public App() {
        this.applicationService = new AppServiceImpl(this);
    }

    public Auftraggeber getAuftraggeber() {
        return this.aktiveAuftraggeber.get(0);
    }
}
