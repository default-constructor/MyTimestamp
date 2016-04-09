package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

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
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("auftraggeber", entity.auftraggeber.getId());
        contentValues.put("entgelt", entity.entgelt.getId());
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Auftrag getInstance(Cursor cursor) {
        Auftrag auftrag = new Auftrag();
        auftrag.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return auftrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auftrag auftrag = (Auftrag) o;

        if (getId() != auftrag.getId()) return false;
        if (getAuftraggeber() != null ? !getAuftraggeber().equals(auftrag.getAuftraggeber()) : auftrag.getAuftraggeber() != null)
            return false;
        return !(getEntgelt() != null ? !getEntgelt().equals(auftrag.getEntgelt()) : auftrag.getEntgelt() != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAuftraggeber() != null ? getAuftraggeber().hashCode() : 0);
        result = 31 * result + (getEntgelt() != null ? getEntgelt().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() { // TODO StringBuilder
        StringBuilder builder = new StringBuilder();
        builder.append("Auftrag {id=").append(this.id).append(", ");
        builder.append("auftraggeber=").append(this.auftraggeber.toString()).append(", ");
        builder.append("entgelt=").append(this.entgelt.toString()).append("}");
        return builder.toString();
    }

    private long id;

    private Auftraggeber auftraggeber;
    private Entgelt entgelt;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Auftraggeber getAuftraggeber() {
        return this.auftraggeber;
    }

    public void setAuftraggeber(Auftraggeber auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public Entgelt getEntgelt() {
        return entgelt;
    }

    public void setEntgelt(Entgelt entgelt) {
        this.entgelt = entgelt;
    }

    public Auftrag() {
        this.auftraggeber = new Auftraggeber();
        this.entgelt = new Entgelt();
    }
}
