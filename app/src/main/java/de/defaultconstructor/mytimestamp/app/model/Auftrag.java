package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.math.BigDecimal;
import java.util.Date;

import de.defaultconstructor.mytimestamp.app.enumeration.Berechnungsfaktor;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

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
        contentValues.put("arbeitsentgelt", String.valueOf(entity.arbeitsentgelt));
        contentValues.put("beginn", DateUtil.getDateStringFromDate(entity.beginn));
        contentValues.put("berechnungsfaktor", entity.berechnungsfaktor.getKuerzel());
        if (null != entity.ende) {
            contentValues.put("ende", DateUtil.getDateStringFromDate(entity.ende));
        }
        contentValues.put("notiz", entity.notiz);
        contentValues.put("auftraggeber", entity.auftraggeber.getId());
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
        auftrag.setArbeitsentgelt(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("arbeitsentgelt"))));
        auftrag.setBeginn(DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("beginn"))));
        auftrag.setBerechnungsfaktor(Berechnungsfaktor.getByKuerzel(cursor.getString(cursor.getColumnIndex("berechnungsfaktor"))));
        auftrag.setEnde(DateUtil.getDateFromString(cursor.getString(cursor.getColumnIndex("ende"))));
        auftrag.setNotiz(cursor.getString(cursor.getColumnIndex("notiz")));
        return auftrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auftrag auftrag = (Auftrag) o;

        if (getId() != auftrag.getId()) return false;
        if (getArbeitsentgelt() != null ? !getArbeitsentgelt().equals(auftrag.getArbeitsentgelt()) : auftrag.getArbeitsentgelt() != null)
            return false;
        if (getBeginn() != null ? !getBeginn().equals(auftrag.getBeginn()) : auftrag.getBeginn() != null)
            return false;
        if (getBerechnungsfaktor() != auftrag.getBerechnungsfaktor()) return false;
        if (getEnde() != null ? !getEnde().equals(auftrag.getEnde()) : auftrag.getEnde() != null)
            return false;
        if (getNotiz() != null ? !getNotiz().equals(auftrag.getNotiz()) : auftrag.getNotiz() != null)
            return false;
        return !(getAuftraggeber() != null ? !getAuftraggeber().equals(auftrag.getAuftraggeber()) : auftrag.getAuftraggeber() != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getArbeitsentgelt() != null ? getArbeitsentgelt().hashCode() : 0);
        result = 31 * result + (getBeginn() != null ? getBeginn().hashCode() : 0);
        result = 31 * result + (getBerechnungsfaktor() != null ? getBerechnungsfaktor().hashCode() : 0);
        result = 31 * result + (getEnde() != null ? getEnde().hashCode() : 0);
        result = 31 * result + (getNotiz() != null ? getNotiz().hashCode() : 0);
        result = 31 * result + (getAuftraggeber() != null ? getAuftraggeber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Auftrag {id=").append(this.id).append(", ");
        builder.append("arbeitsentgelt=").append(this.arbeitsentgelt).append(", ");
        builder.append("beginn=").append(this.beginn).append(", ");
        builder.append("berechnungsfaktor=").append(this.berechnungsfaktor).append(", ");
        builder.append("ende=").append(this.ende).append(", ");
        builder.append("notiz='").append(this.notiz).append("', ");
        builder.append("auftraggeber=").append(this.auftraggeber.toString()).append("}");
        return builder.toString();
    }

    private long id;

    private BigDecimal arbeitsentgelt;
    private Date beginn;
    private Berechnungsfaktor berechnungsfaktor;
    private Date ende;
    private String notiz;

    private Auftraggeber auftraggeber;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getArbeitsentgelt() {
        return this.arbeitsentgelt;
    }

    public void setArbeitsentgelt(BigDecimal arbeitsentgelt) {
        this.arbeitsentgelt = arbeitsentgelt;
    }

    public Date getBeginn() {
        return this.beginn;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public Berechnungsfaktor getBerechnungsfaktor() {
        return this.berechnungsfaktor;
    }

    public void setBerechnungsfaktor(Berechnungsfaktor berechnungsfaktor) {
        this.berechnungsfaktor = berechnungsfaktor;
    }

    public Date getEnde() {
        return this.ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    public String getNotiz() {
        return this.notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public Auftraggeber getAuftraggeber() {
        return this.auftraggeber;
    }

    public void setAuftraggeber(Auftraggeber auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public Auftrag() {
        setArbeitsentgelt(BigDecimal.valueOf(0.0));
        setAuftraggeber(new Auftraggeber());
        setBeginn(new Date());
        setBerechnungsfaktor(Berechnungsfaktor.STUENDLICH);
    }
}
