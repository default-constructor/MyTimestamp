package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.util.DatabaseUtil;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class MainService extends MyTimestampService {

    public static final String TAG = "MainService";

    public MainService(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public List<Auftrag> loadAuftragList() throws ServiceException {
        try {
            this.databaseAdapter.open();
            String tableNameAuftrag = Auftrag.class.getSimpleName().toLowerCase();
            Cursor cursorAuftrag = this.databaseAdapter.select(tableNameAuftrag, null);
            if (null == cursorAuftrag || 0 == cursorAuftrag.getCount()) {
                return new ArrayList<>();
            }
            List<Auftrag> auftragList = new ArrayList<>();
            if (!cursorAuftrag.isAfterLast()) {
                while (cursorAuftrag.moveToNext()) {
                    Auftrag auftrag = (Auftrag) DatabaseUtil.mapResult(cursorAuftrag, tableNameAuftrag);
                    String tableNameAuftraggeber = Auftraggeber.class.getSimpleName().toLowerCase();
                    long idAuftraggeber = cursorAuftrag.getLong(cursorAuftrag.getColumnIndex(tableNameAuftraggeber));
                    Cursor cursorAuftraggeber = this.databaseAdapter.select(tableNameAuftraggeber,
                            "id=" + idAuftraggeber);
                    if (null == cursorAuftraggeber || 0 == cursorAuftraggeber.getCount()) {
                        throw new ServiceException("Kein Ergebnis für Tabelle " + tableNameAuftraggeber);
                    }
                    if (cursorAuftraggeber.moveToFirst()) {
                        auftrag.setAuftraggeber((Auftraggeber) DatabaseUtil.mapResult(cursorAuftraggeber, tableNameAuftraggeber));
                    }
                    auftragList.add(auftrag);
                    cursorAuftrag.moveToNext();
                }
            }
            return auftragList;
        } catch (PersistenceException e) {
            return new ArrayList<>();
        } finally {
            this.databaseAdapter.close();
        }
    }
}
