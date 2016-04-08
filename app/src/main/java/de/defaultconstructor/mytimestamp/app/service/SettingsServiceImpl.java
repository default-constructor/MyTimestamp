package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.SettingsException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Person;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class SettingsServiceImpl extends AppServiceImpl {

    public static final String TAG = "SettingsServiceImpl";

    public SettingsServiceImpl(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public boolean saveSettings(Auftraggeber benutzer, Benutzer auftraggeber) throws SettingsException {
        try {
            this.databaseAdapter.open();
            savePerson(auftraggeber);
            if (!MyTimestamp.currentBenutzer.equals(benutzer)) {
                savePerson(benutzer);
            }
            return true;
        } catch (PersistenceException e) {
            if (e.getCode() == PersistenceException.Cause.UPDATE_NO_CHANGES.getCode()) {
                return true;
            }
            return false;
        } finally {
            this.databaseAdapter.close();
        }
    }

    private void savePerson(Person person) throws PersistenceException, SettingsException {
        long idAdresse = this.databaseAdapter.update(person.getAdresse());
        person.getAdresse().setId(idAdresse);
        long idKontakt = this.databaseAdapter.update(person.getKontakt());
        person.getKontakt().setId(idKontakt);
        if (0 == this.databaseAdapter.update((DatabaseEntity) person)) {
            throw new SettingsException(((DatabaseEntity) person).getClass().getSimpleName() +
                    " konnte nicht gespeichert werden.");
        }
    }
}
