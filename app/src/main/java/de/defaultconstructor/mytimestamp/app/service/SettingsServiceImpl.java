package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class SettingsServiceImpl extends AppServiceImpl {

    private Context context;

    public SettingsServiceImpl(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public long persistAuftraggeber(Auftraggeber auftraggeber) {
        long id = 0;
        try {
            this.databaseAdapter.open();
            long idAdresse = this.databaseAdapter.insert(auftraggeber.getAdresse());
            auftraggeber.getAdresse().setId(idAdresse);
            long idKontakt = this.databaseAdapter.insert(auftraggeber.getKontakt());
            auftraggeber.getKontakt().setId(idKontakt);
            id = this.databaseAdapter.insert(auftraggeber);
            auftraggeber.setId(id);
        } catch (PersistenceException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            this.databaseAdapter.close();
        }
        return id;
    }
}