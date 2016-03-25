package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.enumeration.Status;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 19.03.2016.
 */
public class Auftrag implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Auftrag entity = (Auftrag) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (-1 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("aktiv", entity.isAktiv());
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Auftrag getInstance(Cursor cursor) {
        Auftrag auftrag = new Auftrag(
                Status.getByStatusCode(cursor.getInt(cursor.getColumnIndex("aktiv"))).isEnabled());
        auftrag.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return auftrag;
    }

    private long id = -1; // -1 als Kennzeichen fuer eine neue Entitaet.

    private boolean aktiv;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Auftrag(boolean aktiv) {
        this.aktiv = aktiv;
    }
}
