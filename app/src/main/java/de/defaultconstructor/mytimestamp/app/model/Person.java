package de.defaultconstructor.mytimestamp.app.model;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class Person {

    private Adresse adresse;
    private Kontakt kontakt;

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

    public Person(Adresse adresse, Kontakt kontakt) {
        this.adresse = adresse;
        this.kontakt = kontakt;
    }
}
