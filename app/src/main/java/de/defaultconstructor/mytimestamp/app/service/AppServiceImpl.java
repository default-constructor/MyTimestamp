package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;

import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.*;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class AppServiceImpl {

    public static final String TAG = "AppServiceImpl";

    protected DatabaseAdapter databaseAdapter;

    public AppServiceImpl(Context context) {
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    /**
     * Holt den aktuellen Benutzer.
     *
     * @return den aktuellen Benutzer
     */
    public Benutzer getCurrentBenutzer() {
        try {
            this.databaseAdapter.open();
            Benutzer benutzer = (Benutzer) this.databaseAdapter.select(NAME_TABLE_BENUTZER, /*FIXME "aktiv=1"*/null);
            Log.d(TAG, "getCurrentBenutzer geburtsdatum: " + benutzer.getGeburtsdatum());
            return benutzer;
        } catch (PersistenceException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        } finally {
            this.databaseAdapter.close();
        }
    }

    /**
     * Speichert einen vorlaeufigen Benutzer (Dummy) als Platzhalter fuer den
     * eigentlichen Benutzer.
     *
     * @return die Id des Benutzers oder -1, falls etwas schiefgelaufen ist
     */
    public long persistDummyBenutzer() {
        try {
            this.databaseAdapter.open();
            clearTableBenutzer();
            Benutzer dummyBenutzer = Benutzer.dummy;
            Log.d(TAG, "persistDummyBenutzer geburtsdatum: " + dummyBenutzer.getGeburtsdatum());
            long idAdresse = this.databaseAdapter.insert(dummyBenutzer.getAdresse());
            long idKontakt = this.databaseAdapter.insert(dummyBenutzer.getKontakt());
            dummyBenutzer.getAdresse().setId(idAdresse);
            dummyBenutzer.getKontakt().setId(idKontakt);
            return this.databaseAdapter.insert(dummyBenutzer);
        } catch (PersistenceException e) {
            Log.e(TAG, e.getMessage(), e);
            return -1L;
        } finally {
            this.databaseAdapter.close();
        }
    }

    protected void clearTableBenutzer() {
        this.databaseAdapter.delete(null);
        this.databaseAdapter.delete(null);
        this.databaseAdapter.delete(null);
    }
}
