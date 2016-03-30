package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class Adresse implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Adresse entity = (Adresse) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (-1 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("adresszusatz", entity.adresszusatz);
        contentValues.put("ortschaft", entity.ortschaft);
        contentValues.put("postleitzahl", entity.postleitzahl);
        contentValues.put("staat", entity.staat);
        contentValues.put("straszeUndHaus", entity.straszeUndHaus);
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Adresse getInstance(Cursor cursor) {
        Adresse adresse = new Adresse(
                cursor.getString(cursor.getColumnIndex("adresszusatz")),
                cursor.getString(cursor.getColumnIndex("ortschaft")),
                cursor.getString(cursor.getColumnIndex("postleitzahl")),
                cursor.getString(cursor.getColumnIndex("staat")),
                cursor.getString(cursor.getColumnIndex("straszeUndHaus")));
        return adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adresse adresse = (Adresse) o;

        return getId() == adresse.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Adresse {id=").append(this.id).append(", ");
        buffer.append("adresszusatz='").append(this.adresszusatz).append("', ");
        buffer.append("ortschaft='").append(this.ortschaft).append("', ");
        buffer.append("postleitzahl='").append(this.postleitzahl).append("', ");
        buffer.append("staat=").append(this.staat).append("', ");
        buffer.append("straszeUndHaus='").append(this.straszeUndHaus).append("'}");
        return buffer.toString();
    }

    private long id = -1; // -1 als Kennzeichen fuer eine neue Entitaet.

    private String adresszusatz;
    private String ortschaft;
    private String postleitzahl;
    private String staat;
    private String straszeUndHaus;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getAdresszusatz() {
        return adresszusatz;
    }

    public void setAdresszusatz(String adresszusatz) {
        this.adresszusatz = adresszusatz;
    }

    public String getOrtschaft() {
        return ortschaft;
    }

    public void setOrtschaft(String ortschaft) {
        this.ortschaft = ortschaft;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getStaat() {
        return staat;
    }

    public void setStaat(String staat) {
        this.staat = staat;
    }

    public String getStraszeUndHaus() {
        return straszeUndHaus;
    }

    public void setStraszeUndHaus(String straszeUndHaus) {
        this.straszeUndHaus = straszeUndHaus;
    }

    public Adresse(String adresszusatz, String ortschaft, String postleitzahl, String staat, String straszeUndHaus) {
        this.adresszusatz = adresszusatz;
        this.ortschaft = ortschaft;
        this.postleitzahl = postleitzahl;
        this.staat = staat;
        this.straszeUndHaus = straszeUndHaus;
    }

    public Adresse() {
        //
    }
}
