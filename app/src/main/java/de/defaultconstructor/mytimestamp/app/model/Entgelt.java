package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;

import java.math.BigDecimal;

import de.defaultconstructor.mytimestamp.app.enumeration.Einheit;
import de.defaultconstructor.mytimestamp.app.enumeration.EntgeltHaeufigkeit;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public class Entgelt implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Entgelt entity = (Entgelt) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        return contentValues;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    private long id;

    private BigDecimal betrag;
    private Einheit.Waehrung waehrungeinheit;
    private Einheit.Zeit zeit;
    private EntgeltHaeufigkeit entgeltHaeufigkeit;
}
