package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
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
            return (Benutzer) this.databaseAdapter.select(NAME_TABLE_BENUTZER, "aktiv=1");
        } catch (PersistenceException e) {
            if (e.getCode() == PersistenceException.Cause.SELECT_NO_RESULT.getCode()) {
                Log.i(TAG, e.getMessage());
            }
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
    public Benutzer persistDummies() {
        try {
            this.databaseAdapter.open();
            clearTableBenutzer();/*

            Auftraggeber dummyAuftraggeber = Auftraggeber.DUMMY;
            Log.d(TAG, "persistDummies Auftraggeber: " + dummyAuftraggeber.toString());
            long idAdresse = this.databaseAdapter.insert(dummyAuftraggeber.getAdresse());
            long idKontakt = this.databaseAdapter.insert(dummyAuftraggeber.getKontakt());
            dummyAuftraggeber.getAdresse().setId(idAdresse);
            dummyAuftraggeber.getKontakt().setId(idKontakt);
            long idAuftraggeber = this.databaseAdapter.insert(dummyAuftraggeber);
            dummyAuftraggeber.setId(idAuftraggeber);*/

            Benutzer dummyBenutzer = Benutzer.DUMMY;
            long idAdresse = this.databaseAdapter.insert(dummyBenutzer.getAdresse());
            long idKontakt = this.databaseAdapter.insert(dummyBenutzer.getKontakt());
            dummyBenutzer.getAdresse().setId(idAdresse);
            dummyBenutzer.getKontakt().setId(idKontakt);
            long idBenutzer = this.databaseAdapter.insert(dummyBenutzer);
            dummyBenutzer.setId(idBenutzer);/*

            Auftrag dummyAuftrag = Auftrag.DUMMY;
            Log.d(TAG, "persistDummies Auftrag: " + dummyAuftrag.toString());
            dummyAuftrag.setAuftraggeber(dummyAuftraggeber);
            dummyAuftrag.setBenutzer(dummyBenutzer);
            long idAuftrag = this.databaseAdapter.insert(dummyAuftrag);
            dummyAuftrag.setId(idAuftrag);*/

            return dummyBenutzer;
        } catch (PersistenceException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        } finally {
            this.databaseAdapter.close();
        }
    }

    protected void clearTableBenutzer() {
        this.databaseAdapter.delete(null);
        this.databaseAdapter.delete(null);
        this.databaseAdapter.delete(null);
    }

    public List<Auftraggeber> getAktiveAuftraggeber(Benutzer benutzer) {
        try {
            this.databaseAdapter.open();
            List<Auftraggeber> auftraggeberList = (ArrayList) this.databaseAdapter.selectInnerJoin(
                    NAME_TABLE_AUFTRAGGEBER, NAME_TABLE_AUFTRAG, "id=auftraggeber", "benutzer=" + benutzer.getId());
            return auftraggeberList;
        } catch (PersistenceException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            this.databaseAdapter.close();
        }
    }
}
