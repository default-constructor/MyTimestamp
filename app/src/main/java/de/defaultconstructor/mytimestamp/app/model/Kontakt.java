package de.defaultconstructor.mytimestamp.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class Kontakt implements DatabaseEntity {

    /**
     *
     * @param databaseEntity
     * @return
     */
    public static ContentValues getContentValues(DatabaseEntity databaseEntity) {
        Kontakt entity = (Kontakt) databaseEntity;
        ContentValues contentValues = new ContentValues();
        if (0 < entity.getId()) {
            contentValues.put("id", entity.getId());
        }
        contentValues.put("email", entity.email);
        contentValues.put("mobil", entity.mobil);
        contentValues.put("telefax", entity.telefax);
        contentValues.put("telefon", entity.telefon);
        contentValues.put("webseite", entity.webseite);
        return contentValues;
    }

    /**
     *
     * @param cursor
     * @return
     */
    public static Kontakt getInstance(Cursor cursor) {
        Kontakt kontakt = new Kontakt(
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("mobil")),
                cursor.getString(cursor.getColumnIndex("telefax")),
                cursor.getString(cursor.getColumnIndex("telefon")),
                cursor.getString(cursor.getColumnIndex("webseite")));
        kontakt.setId(cursor.getLong(cursor.getColumnIndex("id")));
        return kontakt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kontakt kontakt = (Kontakt) o;
        return getId() == kontakt.getId();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Kontakt {id=").append(this.id).append(", ");
        builder.append("email='").append(this.email).append("', ");
        builder.append("mobil='").append(this.mobil).append("', ");
        builder.append("telefax='").append(this.telefax).append("', ");
        builder.append("telefon='").append(this.telefon).append("' ");
        builder.append("webseite='").append(this.webseite).append("'}");
        return builder.toString();
    }

    private long id;

    private String email;
    private String mobil;
    private String telefax;
    private String telefon;
    private String webseite;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getTelefax() {
        return telefax;
    }

    public void setTelefax(String telefax) {
        this.telefax = telefax;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getWebseite() {
        return webseite;
    }

    public void setWebseite(String webseite) {
        this.webseite = webseite;
    }

    public Kontakt(String email, String mobil, String telefax, String telefon, String webseite) {
        this.email = email;
        this.mobil = mobil;
        this.telefax = telefax;
        this.telefon = telefon;
        this.webseite = webseite;
    }

    public Kontakt() {
        //
    }
}
