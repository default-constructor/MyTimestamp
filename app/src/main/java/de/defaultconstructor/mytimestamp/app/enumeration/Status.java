package de.defaultconstructor.mytimestamp.app.enumeration;

/**
 * Created by thre on 19.03.2016.
 */
public enum Status {

    AKTIV(1, true),
    GESPERRT(-1, false),
    INAKTIV(0, false);

    public static Status getByStatusCode(int statusCode) {
        for (Status status : values()) {
            if (status.statusCode == statusCode) {
                return status;
            }
        }
        return null;
    }

    private int statusCode;
    private boolean enabled;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    Status(int statusCode, boolean enabled) {
        this.statusCode = statusCode;
        this.enabled = enabled;
    }
}
