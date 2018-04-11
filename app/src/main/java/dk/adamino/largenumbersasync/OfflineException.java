package dk.adamino.largenumbersasync;

/**
 * Created by Adamino.
 */
public class OfflineException extends Exception {

    private String mMessage;

    public OfflineException() {
        mMessage = "Seems we're offline!";
    }

    public OfflineException(String message) {
        mMessage = message;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
