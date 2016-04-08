package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.defaultconstructor.mytimestamp.app.enumeration.Berufsstatus;
import de.defaultconstructor.mytimestamp.app.enumeration.Status;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class Benutzer extends Person implements DatabaseEntity {

    /** Haelt einen {@link Benutzer} als Dummy. */
    public static final Benutzer DUMMY = new Benutzer(true, Berufsstatus.ANGESTELLT, "Mustermann",
            DateUtil.changeDateInYears(-18, new Date()), "Max");

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Benutzer entity = (Benutzer) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("aktiv", entity.aktiv);
        contentValues.put("berufsstatus", entity.berufsstatus.name());
        contentValues.put("familienname", entity.familienname);
        contentValues.put("geburtsdatum", new SimpleDateFormat("dd.MM.yyyy").format(entity.geburtsdatum));
        contentValues.put("vorname", entity.vorname);
        contentValues.put("adresse", (entity.getAdresse().getId()));
        contentValues.put("kontakt", (entity.getKontakt().getId()));
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
                Berufsstatus.valueOf(cursor.getString(cursor.getColumnIndex("berufsstatus"))),
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
        buffer.append("adresse=").append(getAdresse().toString()).append("', ");
        buffer.append("kontakt=").append(getKontakt().toString()).append("'}");
        return buffer.toString();
    }

    private long id;

    private boolean aktiv;
    private Berufsstatus berufsstatus;
    private String familienname;
    private Date geburtsdatum;
    private String vorname;

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

    public Benutzer(boolean aktiv, Berufsstatus berufsstatus, String familienname, Date geburtsdatum,
                    String vorname, Adresse adresse, Kontakt kontakt) {
        super(adresse, kontakt);
        this.aktiv = aktiv;
        this.berufsstatus = berufsstatus;
        this.familienname = familienname;
        this.geburtsdatum = geburtsdatum;
        this.vorname = vorname;
    }

    public Benutzer(boolean aktiv, Berufsstatus berufsstatus, String familienname, Date geburtsdatum,
                    String vorname, Adresse adresse) {
        this(aktiv, berufsstatus, familienname, geburtsdatum, vorname, adresse, new Kontakt());
    }

    public Benutzer(boolean aktiv, Berufsstatus berufsstatus, String familienname, Date geburtsdatum,
                    String vorname, Kontakt kontakt) {
        this(aktiv, berufsstatus, familienname, geburtsdatum, vorname, new Adresse(), kontakt);
    }

    public Benutzer(boolean aktiv, Berufsstatus berufsstatus, String familienname, Date geburtsdatum,
                    String vorname) {
        this(aktiv, berufsstatus, familienname, geburtsdatum, vorname, new Adresse(), new Kontakt());
    }
}
