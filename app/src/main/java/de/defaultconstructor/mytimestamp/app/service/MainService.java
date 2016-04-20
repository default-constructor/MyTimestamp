package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.util.DatabaseUtil;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class MainService extends MyTimestampService {

    public static final String TAG = "MainService";

    public MainService(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public List<Auftrag> loadAbgeschlosseneAuftraege() throws ServiceException {
        return loadAuftragList();
    }

    public List<Auftrag> loadAktuelleAuftraege() throws ServiceException {
        return loadAuftragList();
    }

    public List<Auftrag> loadAnstehendeAuftraege() throws ServiceException {
        List<Auftrag> auftragList = loadAuftragList();
        return auftragList;
    }

    public List<Auftrag> loadAuftragList() throws ServiceException {
        try {
            this.databaseAdapter.open();
            String tableNameAuftrag = Auftrag.class.getSimpleName().toLowerCase();
            Cursor cursorAuftrag = this.databaseAdapter.select(tableNameAuftrag, "benutzer=" +
                    MyTimestamp.currentBenutzer.getId());
            if (null == cursorAuftrag || 0 == cursorAuftrag.getCount()) {
                return new ArrayList<>();
            }
            List<Auftrag> auftragList = new ArrayList<>();
            if (cursorAuftrag.moveToFirst()) {
                while (!cursorAuftrag.isAfterLast()) {
                    Auftrag auftrag = (Auftrag) DatabaseUtil.mapResult(cursorAuftrag, tableNameAuftrag);
                    String tableNameAuftraggeber = Auftraggeber.class.getSimpleName().toLowerCase();
                    long auftraggeberId = cursorAuftrag.getLong(cursorAuftrag.getColumnIndex(tableNameAuftraggeber));
                    Cursor cursorAuftraggeber = this.databaseAdapter.select("auftraggeber", "id=" + auftraggeberId);
                    if (null == cursorAuftraggeber || 0 == cursorAuftraggeber.getCount()) {
                        throw new ServiceException("Kein Ergebnis für Tabelle " + tableNameAuftraggeber);
                    }
                    if (cursorAuftraggeber.moveToFirst()) {
                        auftrag.setAuftraggeber((Auftraggeber) DatabaseUtil.mapResult(cursorAuftraggeber, tableNameAuftraggeber));
                    }
                    long projektId = cursorAuftrag.getLong(cursorAuftrag.getColumnIndex("projekt"));
                    Cursor cursorProjekt = this.databaseAdapter.select("projekt", "id=" + projektId);
                    if (cursorProjekt.moveToFirst()) {
                        auftrag.setProjekt((Projekt) DatabaseUtil.mapResult(cursorProjekt, "projekt"));
                    }
                    auftragList.add(auftrag);
                    cursorAuftrag.moveToNext();
                }
            }
            Log.d(TAG, auftragList.toString());
            return auftragList;
        } catch (PersistenceException e) {
            return new ArrayList<>();
        } finally {
            this.databaseAdapter.close();
        }
    }
}
