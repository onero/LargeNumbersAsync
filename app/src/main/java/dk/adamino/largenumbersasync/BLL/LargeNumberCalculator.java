package dk.adamino.largenumbersasync.BLL;

import dk.adamino.largenumbersasync.DAL.AsyncLargeNumberCalculatorDAO;
import dk.adamino.largenumbersasync.DAL.IAsyncLargeNumberCalculatorDAO;
import dk.adamino.largenumbersasync.IAsyncCalculationCallback;

/**
 * Created by Adamino.
 */
public class LargeNumberCalculator implements ILargeNumberCalculator {

    private IAsyncLargeNumberCalculatorDAO mAsyncLargeNumberCalculatorDAO;

    public LargeNumberCalculator() {
        mAsyncLargeNumberCalculatorDAO = new AsyncLargeNumberCalculatorDAO();
    }

    @Override
    public long add(long number1, long number2) {
        return number1 + number2;
    }

    @Override
    public void addAsync(IAsyncCalculationCallback callback, long number1, long number2) {
        mAsyncLargeNumberCalculatorDAO.getAdditionAsync(callback, number1, number2);
    }
}
