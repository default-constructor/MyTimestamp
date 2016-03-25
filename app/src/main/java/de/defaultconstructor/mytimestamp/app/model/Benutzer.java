package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.defaultconstructor.mytimestamp.app.enumeration.Status;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class Benutzer extends Person implements DatabaseEntity {

    /** Haelt einen {@link Benutzer} als Dummy. */
    public static final Benutzer dummy = new Benutzer(false, "Mustermann",
            DateUtil.changeDateInYears(-18, new Date()), "Max", new Adresse(), new Kontakt());

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Benutzer entity = (Benutzer) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (-1 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        String geburtsdatum = new SimpleDateFormat("dd.MM.yyyy").format(entity.geburtsdatum);
        Log.d("Benutzer", geburtsdatum);
        contentValues.put("aktiv", entity.aktiv);
        contentValues.put("familienname", entity.familienname);
        contentValues.put("geburtsdatum", new SimpleDateFormat("dd.MM.yyyy").format(entity.geburtsdatum));
        contentValues.put("vorname", entity.vorname);
        contentValues.put("adresse", (-1 < entity.adresse.getId() ? entity.adresse.getId() : 0));
        contentValues.put("kontakt", (-1 < entity.kontakt.getId() ? entity.kontakt.getId() : 0));
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Benutzer getInstance(Cursor cursor) {
        Benutzer benutzer = new Benutzer(
                Status.getByStatusCode(cursor.getInt(cursor.getColumnIndex("aktiv"))).isEnabled(),
                cursor.getString(cursor.getColumnIndex("familienname")),
                DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("geburtsdatum"))),
                cursor.getString(cursor.getColumnIndex("vorname")));
        benutzer.setId(cursor.getLong(cursor.getColumnIndex("id")));
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("Benutzer {id=").append(this.id).append(", ");
        buffer.append("aktiv=").append(this.aktiv).append(", ");
        buffer.append("familienname='").append(this.familienname).append("', ");
        buffer.append("geburtsdatum='").append(this.geburtsdatum).append("', ");
        buffer.append("vorname='").append(this.vorname).append("', ");
        buffer.append("adresse=").append(this.adresse.toString()).append("', ");
        buffer.append("kontakt=").append(this.kontakt.toString()).append("'}");
        return buffer.toString();
    }

    private long id = -1; // -1 als Kennzeichen fuer eine neue Entitaet.

    private boolean aktiv;
    private String familienname;
    private Date geburtsdatum;
    private String vorname;

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

    public boolean isAktiv() {
        return this.aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
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
        return this.adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Kontakt getKontakt() {
        return this.kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    public Benutzer(boolean aktiv, String familienname, Date geburtsdatum, String vorname, Adresse adresse, Kontakt kontakt) {
        this.aktiv = aktiv;
        this.familienname = familienname;
        this.geburtsdatum = geburtsdatum;
        this.vorname = vorname;
        this.adresse = adresse;
        this.kontakt = kontakt;
    }

    public Benutzer(boolean aktiv, String familienname, Date geburtsdatum, String vorname, Adresse adresse) {
        this(aktiv, familienname, geburtsdatum, vorname, adresse, new Kontakt());
    }

    public Benutzer(boolean aktiv, String familienname, Date geburtsdatum, String vorname, Kontakt kontakt) {
        this(aktiv, familienname, geburtsdatum, vorname, new Adresse(), kontakt);
    }

    public Benutzer(boolean aktiv, String familienname, Date geburtsdatum, String vorname) {
        this(aktiv, familienname, geburtsdatum, vorname, new Adresse(), new Kontakt());
    }
}
