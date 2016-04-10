package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
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

    public List<Auftrag> loadAuftragList() {
        List<Auftrag> auftragList = new ArrayList<>();
        try {
            this.databaseAdapter.open();
            List<DatabaseEntity> databaseEntityList = this.databaseAdapter.select(Auftrag.class.getSimpleName().toLowerCase(),
                    "beginn<='" + DateUtil.getStringFromDateISO8601(new Date()) + "'");
            for (DatabaseEntity databaseEntity : databaseEntityList) {
                auftragList.add((Auftrag) databaseEntity);
            }
        } catch (PersistenceException e) {
            //
        } finally {
            this.databaseAdapter.close();
        }
        return auftragList;
    }
}
