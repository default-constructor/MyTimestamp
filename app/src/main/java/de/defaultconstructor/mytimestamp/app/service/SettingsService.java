package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
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
        this.databaseAdapter.open();
        try {
            benutzer.getAdresse().setId(this.databaseAdapter.insert(benutzer.getAdresse()).getId());
            benutzer.getKontakt().setId(this.databaseAdapter.insert(benutzer.getKontakt()).getId());
            if (null == this.databaseAdapter.insert(benutzer)) {
                throw new ServiceException(Auftraggeber.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return benutzer;
        } catch (PersistenceException e) {
            throw new ServiceException(Auftraggeber.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }
}
