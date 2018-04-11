package dk.adamino.largenumbersasync.BLL;

import dk.adamino.largenumbersasync.IAsyncCalculationCallback;

/**
 * Created by Adamino.
 */
public interface ILargeNumberCalculator {

    /**
     * Add two large numbers
     * @param number1
     * @param number2
     * @return
     */
    long add(long number1, long number2);

    /**
     * Add two large numbers async
     * @param callback
     * @param number1
     * @param number2
     * @return
     */
    void addAsync(IAsyncCalculationCallback callback, long number1, long number2);
}
