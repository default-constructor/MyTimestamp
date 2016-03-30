package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.enumeration.Status;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 19.03.2016.
 */
public class Auftrag implements DatabaseEntity {

    public static final Auftrag DUMMY = new Auftrag(true, null, null);

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
        contentValues.put("auftraggeber", entity.getAuftraggeber().getId());
        contentValues.put("benutzer", entity.getBenutzer().getId());
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Auftrag getInstance(Cursor cursor) {
        Auftrag auftrag = new Auftrag(
                Status.getByStatusCode(cursor.getInt(cursor.getColumnIndex("aktiv"))).isEnabled(), null, null);
        auftrag.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return auftrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auftrag auftrag = (Auftrag) o;

        if (getId() != auftrag.getId()) return false;
        if (isAktiv() != auftrag.isAktiv()) return false;
        if (getAuftraggeber() != null ? !getAuftraggeber().equals(auftrag.getAuftraggeber()) : auftrag.getAuftraggeber() != null)
            return false;
        return !(getBenutzer() != null ? !getBenutzer().equals(auftrag.getBenutzer()) : auftrag.getBenutzer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (isAktiv() ? 1 : 0);
        result = 31 * result + (getAuftraggeber() != null ? getAuftraggeber().hashCode() : 0);
        result = 31 * result + (getBenutzer() != null ? getBenutzer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() { // TODO StringBuffer
        return "Auftrag{" +
                "id=" + id +
                ", aktiv=" + aktiv +
                ", auftraggeber=" + auftraggeber +
                ", benutzer=" + benutzer +
                '}';
    }

    private long id = -1; // -1 als Kennzeichen fuer eine neue Entitaet.

    private boolean aktiv;

    private Auftraggeber auftraggeber;
    private Benutzer benutzer;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public boolean isAktiv() {
        return this.aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Auftraggeber getAuftraggeber() {
        return this.auftraggeber;
    }

    public void setAuftraggeber(Auftraggeber auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public Benutzer getBenutzer() {
        return this.benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public Auftrag(boolean aktiv, Auftraggeber auftraggeber, Benutzer benutzer) {
        this.aktiv = aktiv;
        this.auftraggeber = auftraggeber;
        this.benutzer = benutzer;
    }
}
