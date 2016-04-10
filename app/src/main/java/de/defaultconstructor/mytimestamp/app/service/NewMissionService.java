package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionService extends MyTimestampService {

    public static final String TAG = "NewMissionService";

    public NewMissionService(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public List<Auftraggeber> loadAuftraggeberList() {
        List<Auftraggeber> listAuftraggeber = new ArrayList<>();
        try {
            this.databaseAdapter.open();
            List<DatabaseEntity> listDatabaseEntity = this.databaseAdapter.select(Auftraggeber.class.getSimpleName().toLowerCase(),
                    Benutzer.class.getSimpleName().toLowerCase() + "=" + MyTimestamp.currentBenutzer.getId());
            for (DatabaseEntity databaseEntity : listDatabaseEntity) {
                listAuftraggeber.add((Auftraggeber) databaseEntity);
            }
        } catch (PersistenceException e) {
            //
        } finally {
            this.databaseAdapter.close();
        }
        return listAuftraggeber;
    }

    public Auftraggeber saveAuftraggeber(Auftraggeber auftraggeber) throws ServiceException {
        try {
            this.databaseAdapter.open();
            auftraggeber.getAdresse().setId(this.databaseAdapter.insert(auftraggeber.getAdresse()).getId());
            auftraggeber.getKontakt().setId(this.databaseAdapter.insert(auftraggeber.getKontakt()).getId());
            if (null == this.databaseAdapter.insert(auftraggeber)) {
                throw new ServiceException(Auftraggeber.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return auftraggeber;
        } catch (PersistenceException e) {
            throw new ServiceException(Auftraggeber.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }

    public Auftrag saveAuftrag(Auftrag auftrag) throws ServiceException {
        try {
            this.databaseAdapter.open();
            if (null == this.databaseAdapter.insert(auftrag)) {
                throw new ServiceException(Auftrag.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return auftrag;
        } catch (PersistenceException e) {
            throw new ServiceException(Auftrag.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }
}
