package model.common.exception;

public class HandbookException extends Exception {
    public HandbookException() {
    }

    public HandbookException(String message) {
        super(message);
    }

    public HandbookException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandbookException(Throwable cause) {
        super(cause);
    }

    public HandbookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
