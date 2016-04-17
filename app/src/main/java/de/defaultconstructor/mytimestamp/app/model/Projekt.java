package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class Projekt implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Projekt entity = (Projekt) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("beginn", DateUtil.getStringFromDateISO8601(entity.beginn));
        contentValues.put("beschreibung", entity.beschreibung);
        contentValues.put("ende", DateUtil.getStringFromDateISO8601(entity.ende));
        contentValues.put("name", entity.name);
        contentValues.put("nummer", entity.nummer);
        contentValues.put("adresse", entity.adresse.getId());
        contentValues.put("kontakt", entity.kontakt.getId());
        return contentValues;
    }

    public static Projekt getInstance(Cursor cursor) {
        Projekt projekt = new Projekt();
        projekt.setId(cursor.getLong(cursor.getColumnIndex("id")));
        projekt.setBeginn(DateUtil.getDateFromStringISO8601(cursor.getString(cursor.getColumnIndex("beginn"))));
        projekt.setBeschreibung(cursor.getString(cursor.getColumnIndex("beschreibung")));
        projekt.setEnde(DateUtil.getDateFromStringISO8601(cursor.getString(cursor.getColumnIndex("ende"))));
        projekt.setName(cursor.getString(cursor.getColumnIndex("name")));
        projekt.setNummer(cursor.getString(cursor.getColumnIndex("nummer")));
        return projekt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projekt projekt = (Projekt) o;

        if (getId() != projekt.getId()) return false;
        if (getBeginn() != null ? !getBeginn().equals(projekt.getBeginn()) : projekt.getBeginn() != null)
            return false;
        if (getBeschreibung() != null ? !getBeschreibung().equals(projekt.getBeschreibung()) : projekt.getBeschreibung() != null)
            return false;
        if (getEnde() != null ? !getEnde().equals(projekt.getEnde()) : projekt.getEnde() != null)
            return false;
        if (getName() != null ? !getName().equals(projekt.getName()) : projekt.getName() != null)
            return false;
        if (getNummer() != null ? !getNummer().equals(projekt.getNummer()) : projekt.getNummer() != null)
            return false;
        if (getAdresse() != null ? !getAdresse().equals(projekt.getAdresse()) : projekt.getAdresse() != null)
            return false;
        return !(getKontakt() != null ? !getKontakt().equals(projekt.getKontakt()) : projekt.getKontakt() != null);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getBeginn() != null ? getBeginn().hashCode() : 0);
        result = 31 * result + (getBeschreibung() != null ? getBeschreibung().hashCode() : 0);
        result = 31 * result + (getEnde() != null ? getEnde().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getNummer() != null ? getNummer().hashCode() : 0);
        result = 31 * result + (getAdresse() != null ? getAdresse().hashCode() : 0);
        result = 31 * result + (getKontakt() != null ? getKontakt().hashCode() : 0);
        return result;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Projekt {id=").append(this.id).append(", ");
        builder.append("beginn=").append(this.beginn).append(", ");
        builder.append("beschreibung='").append(this.beschreibung).append("', ");
        builder.append("ende=").append(this.ende).append(", ");
        builder.append("name='").append(this.name).append("', ");
        builder.append("nummer='").append(this.nummer).append("', ");
        builder.append("adresse=").append(this.adresse.toString()).append(", ");
        builder.append("kontakt=").append(this.kontakt.toString()).append("}");
        return builder.toString();
    }

    private long id;

    private Date beginn;
    private String beschreibung;
    private Date ende;
    private String name;
    private String nummer;

    private Adresse adresse;
    private Kontakt kontakt;

    public Date getBeginn() {
        return this.beginn;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getEnde() {
        return this.ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNummer() {
        return this.nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
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

    public Projekt() {
        setAdresse(new Adresse());
        setBeginn(new Date());
        setKontakt(new Kontakt());
    }
}
