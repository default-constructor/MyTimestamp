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

    /** Haelt den Status der App, ob es sich um den ersten Aufruf handelt. */
    public static boolean firstRun = true;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("preferenceName", MODE_PRIVATE);
        App.firstRun = sharedPreferences.getBoolean("firstRun", true);

        Log.d(TAG, "on create - first run: " + App.firstRun);

        if (App.firstRun) {
            if (-1 == this.applicationService.persistDummyBenutzer()) {
                Log.e(TAG, "Kein Dummy-Benutzer. Die App wird abgebrochen.");
                System.exit(1);
            }
        }

        if (null == (this.currentBenutzer = this.applicationService.getCurrentBenutzer())) {
            Log.e(TAG, "Der aktuelle Benutzer konnte nicht geladen werden. Die App wird abgebrochen.");
            System.exit(1);
        }
    }

    private AppServiceImpl applicationService;

    private Benutzer currentBenutzer;

    private List<Auftraggeber> listAuftraggeber;

    private SharedPreferences sharedPreferences;

    public App() {
        this.applicationService = new AppServiceImpl(this);
    }
}
