package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.enumeration.Berufsstatus;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.*;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class MyTimestampService {

    public static final String TAG = "MyTimestampService";

    protected DatabaseAdapter databaseAdapter;

    public MyTimestampService(Context context) {
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    /**
     * Holt den aktuellen Benutzer.
     *
     * @return den aktuellen Benutzer
     */
    public Benutzer getCurrentBenutzer() {
        try {
            this.databaseAdapter.open();/*
            Benutzer dummy = getDummyBenutzer(); // FIXME wieder ausbauen
            dummy.getAdresse().setId(this.databaseAdapter.insert(dummy.getAdresse()).getId());
            dummy.getKontakt().setId(this.databaseAdapter.insert(dummy.getKontakt()).getId());
            this.databaseAdapter.insert(dummy);*/
            return (Benutzer) this.databaseAdapter.select(NAME_TABLE_BENUTZER, "aktiv=1").get(0);
        } catch (PersistenceException e) {
            if (e.getCode() == PersistenceException.Cause.SELECT_NO_RESULT.getCode()) {
                Log.i(TAG, e.getMessage());
            }
            return null;
        } finally {
            this.databaseAdapter.close();
        }
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

    private Benutzer getDummyBenutzer() { // FIXME wieder ausbauen
        Benutzer benutzer = new Benutzer();
        benutzer.setAktiv(true);
        benutzer.setBerufsstatus(Berufsstatus.ANGESTELLT);
        benutzer.setGeburtsdatum(DateUtil.getDateFromString("03.03.1981"));
        benutzer.setFamilienname("Reno");
        benutzer.setVorname("Thomas");
        return benutzer;
    }
}
