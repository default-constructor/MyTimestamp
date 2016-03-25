package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by thre on 12.03.2016.
 */
public class Auftraggeber extends Person implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Auftraggeber entity = (Auftraggeber) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (-1 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("id", entity.getId());
        contentValues.put("firma", entity.firma);
        contentValues.put("adresse", (-1 < entity.adresse.getId() ? entity.adresse.getId() : 0));
        contentValues.put("kontakt", (-1 < entity.kontakt.getId() ? entity.kontakt.getId() : 0));
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
        auftraggeber.setId(cursor.getLong(cursor.getColumnIndex("id")));
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
        buffer.append("adresse=").append(this.adresse.toString()).append("', ");
        buffer.append("kontakt=").append(this.kontakt.toString()).append("'}");
        return buffer.toString();
    }

    private long id = -1; // -1 als Kennzeichen fuer eine neue Entitaet.

    private String firma;

    private Adresse adresse;
    private Kontakt kontakt;

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

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    public Auftraggeber(String firma, Adresse adresse, Kontakt kontakt) {
        this.firma = firma;
        this.adresse = adresse;
        this.kontakt = kontakt;
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
