package de.defaultconstructor.mytimestamp.app.enumeration;

/**
 * Created by Thomas Reno on 30.03.2016.
 */
public enum Berufsstatus {

    ANGESTELLT("angestellt"),
    FREIBERUFLICH("freiberuflich"),
    KEINEN("keinen"),
    SELBSTAENDIG("selbständig");

    public static Berufsstatus getByBezeichnung(String bezeichnung) {
        for (Berufsstatus berufsstatus : values()) {
            if (berufsstatus.bezeichnung.equals(bezeichnung)) {
                return berufsstatus;
            }
        }
        return null;
    }

    private String bezeichnung;

    public String getBezeichnung() {
        return this.bezeichnung;
    }

    Berufsstatus(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
