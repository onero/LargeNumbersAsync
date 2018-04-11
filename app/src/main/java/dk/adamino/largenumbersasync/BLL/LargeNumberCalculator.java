package dk.adamino.largenumbersasync.BLL;

/**
 * Created by Adamino.
 */
public class LargeNumberCalculator implements ILargeNumberCalculator {

    @Override
    public long add(long number1, long number2) {
        return number1 + number2;
    }
}
