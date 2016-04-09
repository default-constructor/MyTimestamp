package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.math.BigDecimal;

import de.defaultconstructor.mytimestamp.app.enumeration.Einheit;
import de.defaultconstructor.mytimestamp.app.enumeration.Berechnungsfaktor;
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
        if (0 < entity.id) {
            contentValues.put("id", entity.id);
        }
        contentValues.put("betrag", String.valueOf(entity.betrag));
        contentValues.put("haeufigkeit", entity.haeufigkeit.getKuerzel());
        contentValues.put("waehrung", entity.waehrung.getCode());
        return contentValues;
    }

    public static Entgelt getInstance(Cursor cursor) {
        Entgelt entgelt = new Entgelt();
        entgelt.setId(cursor.getLong(cursor.getColumnIndex("id")));
        entgelt.setBetrag(new BigDecimal(cursor.getString(cursor.getColumnIndex("betrag"))));
        entgelt.setHaeufigkeit(Berechnungsfaktor.getByKuerzel(cursor.getString(cursor.getColumnIndex("haeufigkeit"))));
        entgelt.setWaehrung(Einheit.Waehrung.getByCode(cursor.getString(cursor.getColumnIndex("waehrung"))));
        return entgelt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entgelt entgelt = (Entgelt) o;

        return getId() == entgelt.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Entgelt {id=").append(this.id).append(", ");
        builder.append("betrag=").append(this.betrag).append(", ");
        builder.append("haeufigkeit=").append(this.haeufigkeit).append(", ");
        builder.append("waehrung=").append(this.waehrung).append("}");
        return builder.toString();
    }

    private long id;

    private BigDecimal betrag;
    private Berechnungsfaktor haeufigkeit;
    private Einheit.Waehrung waehrung;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBetrag() {
        return this.betrag;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public Berechnungsfaktor getHaeufigkeit() {
        return this.haeufigkeit;
    }

    public void setHaeufigkeit(Berechnungsfaktor haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    public Einheit.Waehrung getWaehrung() {
        return this.waehrung;
    }

    public void setWaehrung(Einheit.Waehrung waehrung) {
        this.waehrung = waehrung;
    }

    public Entgelt() {
        //
    }
}
