package de.defaultconstructor.mytimestamp.app.util;

import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Adresse;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Kontakt;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.NAME_TABLE_ADRESSE;
import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.NAME_TABLE_AUFTRAG;
import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.NAME_TABLE_AUFTRAGGEBER;
import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.NAME_TABLE_BENUTZER;
import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.NAME_TABLE_KONTAKT;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class ServiceUtil {

    /**
     *
     * @param cursor
     * @param tableName
     * @return
     * @throws PersistenceException
     */
    public static DatabaseEntity mapResult(Cursor cursor, String tableName) throws ServiceException {
        switch (tableName) {
            case NAME_TABLE_ADRESSE:
                return Adresse.getInstance(cursor);
            case NAME_TABLE_AUFTRAG:
                return Auftrag.getInstance(cursor);
            case NAME_TABLE_AUFTRAGGEBER:
                return Auftraggeber.getInstance(cursor);
            case NAME_TABLE_BENUTZER:
                return Benutzer.getInstance(cursor);
            case NAME_TABLE_KONTAKT:
                return Kontakt.getInstance(cursor);
            default:
                throw new ServiceException("");
        }
    }

    private ServiceUtil() {
        // util
    }
}
