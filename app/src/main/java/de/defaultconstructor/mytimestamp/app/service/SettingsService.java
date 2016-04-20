package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;

import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Adresse;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Kontakt;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class SettingsService extends MyTimestampService {

    public static final String TAG = "SettingsService";

    public SettingsService(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public Benutzer saveBenutzer(Benutzer benutzer) throws ServiceException {
        benutzer.setAdresse((Adresse) saveDatabaseEntity(benutzer.getAdresse()));
        benutzer.setKontakt((Kontakt) saveDatabaseEntity(benutzer.getKontakt()));
        return (Benutzer) saveDatabaseEntity(benutzer);
    }
}
