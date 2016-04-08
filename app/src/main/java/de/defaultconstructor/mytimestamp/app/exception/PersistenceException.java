package de.defaultconstructor.mytimestamp.app.exception;

/**
 * Created by Thomas Reno on 19.03.2016.
 */
public class PersistenceException extends AppException {

    private int code;

    public int getCode() {
        return this.code;
    }

    public PersistenceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public enum Cause {

        SELECT_NO_RESULT(332, "No result for table {table}."),
        SELECT_TABLE_NOT_FOUND(331, "Table {table} not found."),
        UPDATE_NO_CHANGES(321, "No changes in Table {table}.");

        private int code;
        private String message;

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

        Cause(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
