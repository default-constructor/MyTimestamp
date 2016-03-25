package de.defaultconstructor.mytimestamp.app.enumeration;

/**
 * Created by Thomas Reno on 19.03.2016.
 */
public enum EntgeltHaeufigkeit {

    EINMALIG("e", 0),
    HALBJAEHRLICH("h", 2),
    JAEHRLICH("j", 1),
    MONATLICH("m", 12),
    TAEGLICH("t", 360),
    STUENDLICH("s", 8640),
    VIERTELJAEHRLICH("v", 4);

    public static EntgeltHaeufigkeit getByKuerzel(String kuerzel) {
        for (EntgeltHaeufigkeit entgeltHaeufigkeit : values()) {
            if (entgeltHaeufigkeit.kuerzel.equals(kuerzel)) {
                return entgeltHaeufigkeit;
            }
        }
        return null;
    }

    private String kuerzel;
    private int haeufigkeit;

    public String getKuerzel() {
        return this.kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public int getHaeufigkeit() {
        return this.haeufigkeit;
    }

    public void setHaeufigkeit(int haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    EntgeltHaeufigkeit(String kuerzel, int haeufigkeit) {
        this.kuerzel = kuerzel;
        this.haeufigkeit = haeufigkeit;
    }
}
