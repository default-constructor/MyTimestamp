package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class Projekt implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Projekt entity = (Projekt) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        return contentValues;
    }

    public static Projekt getInstance(Cursor cursor) {
        Projekt projekt = new Projekt();
        return projekt;
    }

    private long id;

    private String beschreibung;
    private String name;
    private String nummer;

    private Adresse adresse;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
