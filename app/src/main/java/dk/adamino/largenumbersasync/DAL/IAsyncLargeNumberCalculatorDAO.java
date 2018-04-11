package dk.adamino.largenumbersasync.DAL;

import dk.adamino.largenumbersasync.IAsyncCalculationCallback;

/**
 * Created by Adamino.
 */
public interface IAsyncLargeNumberCalculatorDAO {

    /**
     * Get remote addition of operators
     * @param number1
     * @param number2
     * @return result as long
     */
    void getAdditionAsync(IAsyncCalculationCallback callback, long number1, long number2);
}
