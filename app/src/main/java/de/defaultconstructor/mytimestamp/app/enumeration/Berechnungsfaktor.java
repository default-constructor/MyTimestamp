package de.defaultconstructor.mytimestamp.app.enumeration;

/**
 * Created by Thomas Reno on 19.03.2016.
 */
public enum Berechnungsfaktor {

    EINMALIG("e", "einmalig", 0),
    HALBJAEHRLICH("h", "halbjährlich", 2),
    JAEHRLICH("j", "jährlich", 1),
    MONATLICH("m", "monatlich", 12),
    WOECHENTLICH("w", "wöchentlich", 52),
    TAEGLICH("t", "täglich", 360),
    STUENDLICH("s", "stündlich", 8640),
    VIERTELJAEHRLICH("v", "vierteljährlich", 4);

    public static Berechnungsfaktor getByKuerzel(String kuerzel) {
        for (Berechnungsfaktor berechnungsfaktor : values()) {
            if (berechnungsfaktor.kuerzel.equals(kuerzel)) {
                return berechnungsfaktor;
            }
        }
        return null;
    }

    public static Berechnungsfaktor getByBezeichnung(String bezeichnung) {
        for (Berechnungsfaktor berechnungsfaktor : values()) {
            if (berechnungsfaktor.bezeichnung.equals(bezeichnung)) {
                return berechnungsfaktor;
            }
        }
        return null;
    }

    private String kuerzel;
    private String bezeichnung;
    private int haeufigkeit;

    public String getKuerzel() {
        return this.kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getHaeufigkeit() {
        return this.haeufigkeit;
    }

    public void setHaeufigkeit(int haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    Berechnungsfaktor(String kuerzel, String bezeichnung, int haeufigkeit) {
        this.kuerzel = kuerzel;
        this.bezeichnung = bezeichnung;
        this.haeufigkeit = haeufigkeit;
    }
}
