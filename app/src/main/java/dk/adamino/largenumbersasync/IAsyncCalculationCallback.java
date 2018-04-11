package dk.adamino.largenumbersasync;

/**
 * Created by Adamino.
 */
public interface IAsyncCalculationCallback {

    /**
     * When result is back from async calculation
     * @param result
     */
    void onResult(long result);
}
