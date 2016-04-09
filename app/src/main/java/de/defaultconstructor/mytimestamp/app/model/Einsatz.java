package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.math.BigDecimal;
import java.util.Date;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class Einsatz implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Einsatz entity = (Einsatz) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("beginn", DateUtil.getStringFromDate(entity.beginn));
        contentValues.put("beschreibung", entity.beschreibung);
        contentValues.put("ende", DateUtil.getStringFromDate(entity.ende));
        contentValues.put("fahrtzeit", String.valueOf(entity.fahrtzeit));
        contentValues.put("pause", String.valueOf(entity.pause));
        return contentValues;
    }

    public static Einsatz getInstance(Cursor cursor) {
        Einsatz einsatz = new Einsatz();
        einsatz.setBeginn(DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("beginn"))));
        einsatz.setBeschreibung(cursor.getString(cursor.getColumnIndex("beschreibung")));
        einsatz.setEnde(DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("ende"))));
        einsatz.setFahrtzeit(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("fahrtzeit"))));
        einsatz.setPause(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("pause"))));
        return einsatz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Einsatz einsatz = (Einsatz) o;

        if (getId() != einsatz.getId()) return false;
        if (getBeginn() != null ? !getBeginn().equals(einsatz.getBeginn()) : einsatz.getBeginn() != null)
            return false;
        if (getBeschreibung() != null ? !getBeschreibung().equals(einsatz.getBeschreibung()) : einsatz.getBeschreibung() != null)
            return false;
        if (getEnde() != null ? !getEnde().equals(einsatz.getEnde()) : einsatz.getEnde() != null)
            return false;
        if (getFahrtzeit() != null ? !getFahrtzeit().equals(einsatz.getFahrtzeit()) : einsatz.getFahrtzeit() != null)
            return false;
        return !(getPause() != null ? !getPause().equals(einsatz.getPause()) : einsatz.getPause() != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getBeginn() != null ? getBeginn().hashCode() : 0);
        result = 31 * result + (getBeschreibung() != null ? getBeschreibung().hashCode() : 0);
        result = 31 * result + (getEnde() != null ? getEnde().hashCode() : 0);
        result = 31 * result + (getFahrtzeit() != null ? getFahrtzeit().hashCode() : 0);
        result = 31 * result + (getPause() != null ? getPause().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Einsatz {id=").append(this.id).append(", ");
        builder.append("beginn=").append(this.beginn).append(", ");
        builder.append("beschreibung=").append(this.beschreibung).append(", ");
        builder.append("ende=").append(this.ende).append(", ");
        builder.append("fahrtzeit=").append(this.fahrtzeit).append(", ");
        builder.append("pause=").append(this.pause).append("}");
        return builder.toString();
    }

    private long id;

    private Date beginn;
    private String beschreibung;
    private Date ende;
    private BigDecimal fahrtzeit;
    private BigDecimal pause;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Date getBeginn() {
        return beginn;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public BigDecimal getFahrtzeit() {
        return fahrtzeit;
    }

    public void setFahrtzeit(BigDecimal fahrtzeit) {
        this.fahrtzeit = fahrtzeit;
    }

    public BigDecimal getPause() {
        return pause;
    }

    public void setPause(BigDecimal pause) {
        this.pause = pause;
    }

    public Einsatz() {
        //
    }
}
