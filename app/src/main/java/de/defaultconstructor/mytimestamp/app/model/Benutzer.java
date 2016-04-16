package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import de.defaultconstructor.mytimestamp.app.enumeration.Berufsstatus;
import de.defaultconstructor.mytimestamp.app.enumeration.Status;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class Benutzer extends Person implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Benutzer entity = (Benutzer) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.id) {
            contentValues.put("id", entity.id);
        }
        contentValues.put("aktiv", entity.aktiv);
        contentValues.put("berufsstatus", entity.berufsstatus.name());
        contentValues.put("familienname", entity.familienname);
        contentValues.put("geburtsdatum", DateUtil.getDateStringFromDate(entity.geburtsdatum));
        contentValues.put("vorname", entity.vorname);
        contentValues.put("adresse", (entity.adresse.getId()));
        contentValues.put("kontakt", (entity.kontakt.getId()));
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Benutzer getInstance(Cursor cursor) {
        Benutzer benutzer = new Benutzer();
        benutzer.setId(cursor.getLong(cursor.getColumnIndex("id")));
        benutzer.setAktiv(Status.getByStatusCode(cursor.getInt(cursor.getColumnIndex("aktiv"))).isEnabled());
        benutzer.setBerufsstatus(Berufsstatus.valueOf(cursor.getString(cursor.getColumnIndex("berufsstatus"))));
        benutzer.setFamilienname(cursor.getString(cursor.getColumnIndex("familienname")));
        benutzer.setGeburtsdatum(DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("geburtsdatum"))));
        benutzer.setVorname(cursor.getString(cursor.getColumnIndex("vorname")));
        return benutzer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Benutzer benutzer = (Benutzer) o;
        if (!getFamilienname().equals(benutzer.getFamilienname())) return false;
        if (!getGeburtsdatum().equals(benutzer.getGeburtsdatum())) return false;
        return getVorname().equals(benutzer.getVorname());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFamilienname().hashCode();
        result = 31 * result + getGeburtsdatum().hashCode();
        result = 31 * result + getVorname().hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Benutzer {id=").append(this.id).append(", ");
        builder.append("aktiv=").append(this.aktiv).append(", ");
        builder.append("familienname='").append(this.familienname).append("', ");
        builder.append("geburtsdatum='").append(this.geburtsdatum).append("', ");
        builder.append("vorname='").append(this.vorname).append("', ");
        builder.append("adresse=").append(this.adresse.toString()).append(", ");
        builder.append("kontakt=").append(this.kontakt.toString()).append("}");
        return builder.toString();
    }

    private long id;

    private boolean aktiv;
    private Berufsstatus berufsstatus;
    private String familienname;
    private Date geburtsdatum;
    private String vorname;

    private Adresse adresse;
    private Kontakt kontakt;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAktiv() {
        return this.aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Berufsstatus getBerufsstatus() {
        return this.berufsstatus;
    }

    public void setBerufsstatus(Berufsstatus berufsstatus) {
        this.berufsstatus = berufsstatus;
    }

    public String getFamilienname() {
        return this.familienname;
    }

    public void setFamilienname(String familienname) {
        this.familienname = familienname;
    }

    public Date getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
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

    public Benutzer() {
        this.adresse = new Adresse();
        this.kontakt = new Kontakt();
    }
}
