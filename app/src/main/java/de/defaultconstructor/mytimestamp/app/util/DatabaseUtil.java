package de.defaultconstructor.mytimestamp.app.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.model.Adresse;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Kontakt;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.*;

/**
 * Created by thre on 19.03.2016.
 */
public final class DatabaseUtil {

    public static final String TAG = "DatabaseUtil";

    private DatabaseUtil() {
        // Util class
    }

    /**
     *
     * @param tableName
     * @return
     * @throws PersistenceException
     */
    public static String[] getTableColumns(String tableName) throws PersistenceException {
        switch (tableName) {
            case NAME_TABLE_ADRESSE:
                return COLUMNS_TABLE_ADRESSE;
            case NAME_TABLE_AUFTRAG:
                return COLUMNS_TABLE_AUFTRAG;
            case NAME_TABLE_AUFTRAGGEBER:
                return COLUMNS_TABLE_AUFTRAGGEBER;
            case NAME_TABLE_BENUTZER:
                return COLUMNS_TABLE_BENUTZER;
            case NAME_TABLE_KONTAKT:
                return COLUMNS_TABLE_KONTAKT;
            default:
                throw new PersistenceException(
                        MESSAGE_ERROR_TABLE_NOT_FOUND.replace("{tableName}", tableName));
        }
    }

    /**
     *
     * @param cursor
     * @param tableName
     * @return
     * @throws PersistenceException
     */
    public static DatabaseEntity mapResult(Cursor cursor, String tableName) throws PersistenceException {
        Log.d(TAG, "mapResult Cursor count: " + cursor.getCount());
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
                throw new PersistenceException(
                        MESSAGE_ERROR_TABLE_NOT_FOUND.replace("{tableName}", tableName));
        }
    }

    /**
     *
     * @param databaseEntity
     * @param tableName
     * @return
     * @throws PersistenceException
     */
    public static ContentValues mapDatabaseEntity(DatabaseEntity databaseEntity, String tableName)
            throws PersistenceException {
        switch (tableName) {
            case NAME_TABLE_ADRESSE:
                return Adresse.getContentValues(databaseEntity);
            case NAME_TABLE_AUFTRAG:
                return Auftrag.getContentValues(databaseEntity);
            case NAME_TABLE_AUFTRAGGEBER:
                return Auftraggeber.getContentValues(databaseEntity);
            case NAME_TABLE_BENUTZER:
                return Benutzer.getContentValues(databaseEntity);
            case NAME_TABLE_KONTAKT:
                return Kontakt.getContentValues(databaseEntity);
            default:
                throw new PersistenceException(
                        MESSAGE_ERROR_TABLE_NOT_FOUND.replace("{tableName}", tableName));
        }
    }
}
