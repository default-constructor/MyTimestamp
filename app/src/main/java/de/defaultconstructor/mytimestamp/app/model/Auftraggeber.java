package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 12.03.2016.
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
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("firma", entity.firma);
        contentValues.put("adresse", entity.adresse.getId());
        contentValues.put("benutzer", entity.benutzer.getId());
        contentValues.put("kontakt", entity.kontakt.getId());
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Auftraggeber getInstance(Cursor cursor) {
        Auftraggeber auftraggeber = new Auftraggeber();
        auftraggeber.setId(cursor.getLong(cursor.getColumnIndex("id")));
        auftraggeber.setFirma(cursor.getString(cursor.getColumnIndex("firma")));
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
        StringBuilder builder = new StringBuilder();
        builder.append("Auftraggeber {id=").append(this.id).append(", ");
        builder.append("firma='").append(this.firma).append("', ");
        builder.append("adresse=").append(this.adresse.toString()).append(", ");
        builder.append("benutzer=").append(this.benutzer.toString()).append(", ");
        builder.append("kontakt=").append(this.kontakt.toString()).append("}");
        return builder.toString();
    }

    private long id;

    private String firma;

    private Adresse adresse;
    private Benutzer benutzer;
    private Kontakt kontakt;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getFirma() {
        return this.firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Benutzer getBenutzer() {
        return this.benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public Kontakt getKontakt() {
        return this.kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    public Auftraggeber() {
        this.adresse = new Adresse();
        this.benutzer = new Benutzer();
        this.kontakt = new Kontakt();
    }
}
