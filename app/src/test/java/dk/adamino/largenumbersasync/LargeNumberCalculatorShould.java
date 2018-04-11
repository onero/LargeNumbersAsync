package dk.adamino.largenumbersasync;

import org.junit.Test;

import dk.adamino.largenumbersasync.BLL.ILargeNumberCalculator;
import dk.adamino.largenumbersasync.BLL.LargeNumberCalculator;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LargeNumberCalculatorShould {

    private ILargeNumberCalculator mLargeNumberCalculator;

    public LargeNumberCalculatorShould() {
        mLargeNumberCalculator = new LargeNumberCalculator();
    }

    @Test
    public void addTwoLongs() {
        long number1 = 999_999_999;
        long number2 = 999_999_999;

        long expextedResult = number1 + number2;
        long result = mLargeNumberCalculator.add(number1, number2);
        assertEquals(expextedResult, result);
    }
}