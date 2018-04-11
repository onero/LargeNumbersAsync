package dk.adamino.largenumbersasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dk.adamino.largenumbersasync.BLL.ILargeNumberCalculator;
import dk.adamino.largenumbersasync.BLL.LargeNumberCalculator;

public class MainActivity extends AppCompatActivity implements IAsyncCalculationCallback {

    private EditText mNumber1, mNumber2;
    private TextView mResult;

    private ILargeNumberCalculator mLargeNumberCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumber1 = findViewById(R.id.txtNumber1);
        mNumber2 = findViewById(R.id.txtNumber2);
        mResult = findViewById(R.id.txtResult);

        mLargeNumberCalculator = new LargeNumberCalculator();
    }

    public void onCalculate(View view) {
        long input1 = 0, input2 = 0;
        if (hasInput(mNumber1)) {
            input1 = getInt(mNumber1);
        }
        if (hasInput(mNumber2)) {
            input2 = getInt(mNumber2);
        }

        try {
            mLargeNumberCalculator.addAsync(this, input1, input2);
        } catch (Exception e) {
            long result = mLargeNumberCalculator.add(input1, input2);
            mResult.setText(result + "");
        }
    }


    /***
     * Get int from input
     * @param numberInput
     * @return input as int
     */
    private int getInt(TextView numberInput) {
        return Integer.parseInt(numberInput.getText().toString());
    }

    /***
     * Verify that the input has value
     * @param numberInput
     * @return true if input has value, else false
     */
    private boolean hasInput(TextView numberInput) {
        return numberInput.getText().length() > 0;
    }

    @Override
    public void onResult(long result) {
        mResult.setText(result + "");
    }
}
