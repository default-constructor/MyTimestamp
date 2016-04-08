package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 12.03.2016.
 */
public class Auftraggeber extends Person implements DatabaseEntity {

    public static final Auftraggeber DUMMY = new Auftraggeber("Die Firma");

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Auftraggeber entity = (Auftraggeber) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("firma", entity.firma);
        contentValues.put("adresse", (entity.getAdresse().getId()));
        contentValues.put("kontakt", (entity.getKontakt().getId()));
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Auftraggeber getInstance(Cursor cursor) {
        Auftraggeber auftraggeber = new Auftraggeber(
                cursor.getString(cursor.getColumnIndex("firma")));
        Log.d("Auftraggeber", "" + cursor.getColumnCount());
    //    auftraggeber.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return auftraggeber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Auftraggeber that = (Auftraggeber) o;

        return getFirma().equals(that.getFirma());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFirma().hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Auftraggeber {id=").append(this.id).append(", ");
        buffer.append("firma='").append(this.firma).append("', ");
        buffer.append("adresse=").append(this.getAdresse().toString()).append("', ");
        buffer.append("kontakt=").append(this.getKontakt().toString()).append("'}");
        return buffer.toString();
    }

    private long id;

    private String firma;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Auftraggeber(String firma, Adresse adresse, Kontakt kontakt) {
        super(adresse, kontakt);
        this.firma = firma;
    }

    public Auftraggeber(String firma, Adresse adresse) {
        this(firma, adresse, new Kontakt());
    }

    public Auftraggeber(String firma, Kontakt kontakt) {
        this(firma, new Adresse(), kontakt);
    }

    public Auftraggeber(String firma) {
        this(firma, new Adresse(), new Kontakt());
    }
}
