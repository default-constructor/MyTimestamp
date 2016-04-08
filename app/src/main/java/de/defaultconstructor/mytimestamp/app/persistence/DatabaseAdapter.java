package de.defaultconstructor.mytimestamp.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.App;
import de.defaultconstructor.mytimestamp.app.enumeration.EntgeltHaeufigkeit;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.util.DatabaseUtil;
import de.defaultconstructor.mytimestamp.app.util.StringUtil;

import static de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter.DatabaseConstants.*;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class DatabaseAdapter {

    public static final String TAG = "DatabaseAdapter";

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DatabaseAdapter(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * Opens the connection of the database.
     *
     * @return the database adapter
     */
    public DatabaseAdapter open() {
        this.database = this.databaseHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the connection of the database.
     */
    public void close() {
        this.databaseHelper.close();
    }

    /**
     * Deletes desired entity of the appropriate table.
     *
     * @param databaseEntity
     *              {@link DatabaseEntity}
     *
     * @return the count of deleted rows
     */
    public int delete(DatabaseEntity databaseEntity) {
        if (null == databaseEntity) { // FIXME wieder ausbauen
            return delete("benutzer", null);
        }
        String tableName = databaseEntity.getClass().getSimpleName().toLowerCase();
        String whereClause = "id=" + databaseEntity.getId();
        return delete(tableName, whereClause);
    }

    /**
     * Inserts the given entity into the appropriate table.
     *
     * @param databaseEntity
     *              {@link DatabaseEntity}
     *
     * @return the id of the inserted entity
     *
     * @throws PersistenceException
     */
    public long insert(DatabaseEntity databaseEntity) throws PersistenceException {
        Log.d(TAG, "insert " + databaseEntity.getClass().getSimpleName());
        String table = databaseEntity.getClass().getSimpleName().toLowerCase();
        return insert(table, databaseEntity);
    }

    /**
     * Selects a entity from the desired table in accordance with the given contitions.
     *
     * @param tableName
     *              String
     * @param whereClause
     *              String
     *
     * @return the selected entity
     *
     * @throws {@link PersistenceException}
     */
    public DatabaseEntity select(String tableName, String whereClause) throws PersistenceException {
        whereClause = null != whereClause ? whereClause : "id > 0";
        SQLiteCursor cursor = (SQLiteCursor) this.database.query(true, tableName,
                DatabaseUtil.getColumnNames(tableName), whereClause, null, null, null, null, null);
        if (null == cursor || 0 == cursor.getCount()) {
            throw new PersistenceException(PersistenceException.Cause.SELECT_NO_RESULT.getCode(),
                    PersistenceException.Cause.SELECT_NO_RESULT.getMessage().replace("{table}", tableName));
        }
        cursor.moveToFirst();
        DatabaseEntity entity = DatabaseUtil.mapResult(cursor, tableName);
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return entity;
    }

    /**
     *
     * @param tableName
     * @param joinedTable
     * @param onClause
     * @param whereClause
     * @return
     * @throws PersistenceException
     */
    public List<DatabaseEntity> selectInnerJoin(String tableName, String joinedTable, String onClause,
                                                String whereClause) throws PersistenceException {
        String leftJoin = tableName + "." + onClause.substring(0, onClause.indexOf("="));
        String rightJoin = joinedTable + "." + onClause.substring(onClause.indexOf("=") + 1);
        String sql = "SELECT " + StringUtil.getStringedArray(DatabaseUtil.getColumnNames(tableName), ",") +
                " FROM " + tableName + " INNER JOIN " + joinedTable + " ON " + leftJoin + "=" + rightJoin +
                " WHERE " + whereClause + ";";
        Cursor cursor = this.database.rawQuery(sql, null);
        if (null == cursor || 0 == cursor.getCount()) {
            throw new PersistenceException(PersistenceException.Cause.SELECT_NO_RESULT.getCode(),
                    PersistenceException.Cause.SELECT_NO_RESULT.getMessage().replace("{table}", tableName));
        }
        List<DatabaseEntity> databaseEntityList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                databaseEntityList.add(DatabaseUtil.mapResult(cursor, tableName));
                cursor.moveToNext();
            }
        }
        return databaseEntityList;
    }

    /**
     * Updates the appropriate table by the given entity.
     *
     * @param databaseEntity
     *              {@link DatabaseEntity}
     *
     * @return the id of the updated entity
     */
    public long update(DatabaseEntity databaseEntity) throws PersistenceException {
        String tableName = databaseEntity.getClass().getSimpleName().toLowerCase();
        return update(tableName, databaseEntity);
    }

    private int delete(String tableName, String whereClause) {
        return this.database.delete(tableName, whereClause, null);
    }

    private long insert(String tableName, DatabaseEntity databaseEntity) throws PersistenceException {
        ContentValues values = DatabaseUtil.mapDatabaseEntity(databaseEntity, tableName);
        return this.database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    private long update(String tableName, DatabaseEntity databaseEntity) throws PersistenceException {
        String[] columns = DatabaseUtil.getColumnNames(tableName);
        ContentValues values = DatabaseUtil.mapDatabaseEntity(databaseEntity, tableName);
        int rows = this.database.updateWithOnConflict(tableName, values, "id=" + databaseEntity.getId(),
                null, SQLiteDatabase.CONFLICT_ROLLBACK);
        if (0 == rows) {
            throw new PersistenceException(PersistenceException.Cause.UPDATE_NO_CHANGES.getCode(),
                    PersistenceException.Cause.UPDATE_NO_CHANGES.getMessage().replace("{table}", tableName));
        }
        return databaseEntity.getId();
    }

    public static final class DatabaseConstants {

        public static final String MESSAGE_ERROR_DATABASE_TRANSACTION_FAILED = "Database transaction failed: {reason}";
        public static final String MESSAGE_ERROR_TABLE_NOT_FOUND = "Table {tableName} not found.";
        public static final String MESSAGE_INFO_NO_RESULT = "No result for table {tableName}.";

        public static final String NAME_TABLE_ADRESSE = "adresse";
        public static final String NAME_TABLE_AUFTRAG = "auftrag";
        public static final String NAME_TABLE_AUFTRAGGEBER = "auftraggeber";
        public static final String NAME_TABLE_BENUTZER = "benutzer";
        public static final String NAME_TABLE_KONTAKT = "kontakt";

        public static final String[] COLUMNS_TABLE_ADRESSE = new String[] {
                "id", "adresszusatz", "ortschaft", "postleitzahl", "staat", "straszeUndHaus"};
        public static final String[] COLUMNS_TABLE_AUFTRAG = new String[] {
                "id", "aktiv", "auftragsart", "entgelt", "entgeltHaeufigkeit", NAME_TABLE_BENUTZER, NAME_TABLE_AUFTRAGGEBER};
        public static final String[] COLUMNS_TABLE_AUFTRAGGEBER = new String[] {
                "id", "firma", NAME_TABLE_ADRESSE, NAME_TABLE_KONTAKT};
        public static final String[] COLUMNS_TABLE_BENUTZER = new String[] {
                "id", "aktiv", "berufsstatus", "familienname", "geburtsdatum", "vorname", NAME_TABLE_ADRESSE, NAME_TABLE_KONTAKT};
        public static final String[] COLUMNS_TABLE_KONTAKT = new String[] {
                "id", "email", "mobil", "telefax", "telefon", "webseite"};

        protected static final String NAME_DATABASE = "myTimestamp";

        static final String SQL_CREATE_TABLE_ADRESSE =
                "CREATE TABLE " + NAME_TABLE_ADRESSE + " (" +
                        COLUMNS_TABLE_ADRESSE[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNS_TABLE_ADRESSE[1] + " TEXT, " +
                        COLUMNS_TABLE_ADRESSE[2] + " TEXT, " +
                        COLUMNS_TABLE_ADRESSE[3] + " TEXT, " +
                        COLUMNS_TABLE_ADRESSE[4] + " TEXT, " +
                        COLUMNS_TABLE_ADRESSE[5] + " TEXT);";
        static final String SQL_CREATE_TABLE_AUFTRAG =
                "CREATE TABLE " + NAME_TABLE_AUFTRAG + " (" +
                        COLUMNS_TABLE_AUFTRAG[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNS_TABLE_AUFTRAG[1] + " INTEGER DEFAULT 0, " +
                        COLUMNS_TABLE_AUFTRAG[2] + " TEXT, " + // TODO auftragsart TEXT NOT NULL
                        COLUMNS_TABLE_AUFTRAG[3] + " REAL, " +
                        COLUMNS_TABLE_AUFTRAG[4] + " INTEGER DEFAULT " + EntgeltHaeufigkeit.EINMALIG.getHaeufigkeit() + ", " +
                        COLUMNS_TABLE_AUFTRAG[5] + " INTEGER NOT NULL, " +
                        COLUMNS_TABLE_AUFTRAG[6] + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_AUFTRAG[5] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_AUFTRAG[5] + " (id), " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_AUFTRAG[6] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_AUFTRAG[6] + "(id));";
        static final String SQL_CREATE_TABLE_AUFTRAGGEBER =
                "CREATE TABLE " + NAME_TABLE_AUFTRAGGEBER + " (" +
                        COLUMNS_TABLE_AUFTRAGGEBER[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNS_TABLE_AUFTRAGGEBER[1] + " TEXT NOT NULL, " +
                        COLUMNS_TABLE_AUFTRAGGEBER[2] + " INTEGER, " +
                        COLUMNS_TABLE_AUFTRAGGEBER[3] + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_AUFTRAGGEBER[2] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_AUFTRAGGEBER[2] + " (id), " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_AUFTRAGGEBER[3] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_AUFTRAGGEBER[3] + " (id));";
        static final String SQL_CREATE_TABLE_BENUTZER =
                "CREATE TABLE " + NAME_TABLE_BENUTZER + " (" +
                        COLUMNS_TABLE_BENUTZER[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNS_TABLE_BENUTZER[1] + " INTEGER DEFAULT 0, " +
                        COLUMNS_TABLE_BENUTZER[2] + " TEXT NOT NULL, " +
                        COLUMNS_TABLE_BENUTZER[3] + " DATE NOT NULL, " +
                        COLUMNS_TABLE_BENUTZER[4] + " TEXT NOT NULL, " +
                        COLUMNS_TABLE_BENUTZER[5] + " TEXT NOT NULL, " +
                        COLUMNS_TABLE_BENUTZER[6] + " INTEGER, " +
                        COLUMNS_TABLE_BENUTZER[7] + " INTEGER, " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_BENUTZER[6] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_BENUTZER[6] + " (id), " +
                        "FOREIGN KEY (" + COLUMNS_TABLE_BENUTZER[7] + ") " +
                        "REFERENCES " + COLUMNS_TABLE_BENUTZER[7] + " (id));";
        static final String SQL_CREATE_TABLE_KONTAKT =
                "CREATE TABLE " + NAME_TABLE_KONTAKT + " (" +
                        COLUMNS_TABLE_KONTAKT[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNS_TABLE_KONTAKT[1] + " TEXT, " +
                        COLUMNS_TABLE_KONTAKT[2] + " TEXT, " +
                        COLUMNS_TABLE_KONTAKT[3] + " TEXT, " +
                        COLUMNS_TABLE_KONTAKT[4] + " TEXT, " +
                        COLUMNS_TABLE_KONTAKT[5] + " TEXT);";

        static final int VERSION_DATABASE = 1;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public static final String TAG = "DatabaseHelper";

        private SQLiteDatabase sqliteDatabase;

        @Override
        public void onCreate(SQLiteDatabase db) {
            this.sqliteDatabase = db;
            if (App.firstRun) {
                createTables();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("DatabaseHelper", "Upgrading applications database from version " + oldVersion +
                    " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_BENUTZER);
            onCreate(db);
        }

        public DatabaseHelper(Context context) {
            super(context, NAME_DATABASE, null, VERSION_DATABASE);
        }

        private void createTables() {
            this.sqliteDatabase.execSQL(SQL_CREATE_TABLE_ADRESSE);
            this.sqliteDatabase.execSQL(SQL_CREATE_TABLE_KONTAKT);
            this.sqliteDatabase.execSQL(SQL_CREATE_TABLE_AUFTRAGGEBER);
            this.sqliteDatabase.execSQL(SQL_CREATE_TABLE_BENUTZER);
            this.sqliteDatabase.execSQL(SQL_CREATE_TABLE_AUFTRAG);
        }
    }
}
