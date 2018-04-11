package dk.adamino.largenumbersasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import dk.adamino.largenumbersasync.BLL.ILargeNumberCalculator;
import dk.adamino.largenumbersasync.BLL.LargeNumberCalculator;

public class MainActivity extends AppCompatActivity implements IAsyncCalculationCallback {

    public static final int ERROR_RESULT = -1;
    private EditText mNumber1, mNumber2;
    private TextView mResult;
    private ProgressBar mProgressBar;

    private ILargeNumberCalculator mLargeNumberCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumber1 = findViewById(R.id.txtNumber1);
        mNumber2 = findViewById(R.id.txtNumber2);
        mResult = findViewById(R.id.txtResult);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mLargeNumberCalculator = new LargeNumberCalculator();
    }

    public void onCalculate(View view) {
        long input1 = 0, input2 = 0;
        if (hasInput(mNumber1)) {
            input1 = getLong(mNumber1);
        }
        if (hasInput(mNumber2)) {
            input2 = getLong(mNumber2);
        }

        try {
            mLargeNumberCalculator.addAsync(this, input1, input2);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.animate();
        } catch (Exception oe) {
            Log.e("GUI", oe.getMessage());
        }
    }


    /***
     * Get long from input
     * @param numberInput
     * @return input as long
     */
    private long getLong(TextView numberInput) {
        return Long.parseLong(numberInput.getText().toString());
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
        if (result != ERROR_RESULT) {
            mResult.setText(result + "");
            mProgressBar.setVisibility(View.INVISIBLE);

        } else {
            long offlineResult = mLargeNumberCalculator.add(getLong(mNumber1), getLong(mNumber2));
            mResult.setText("Offline result: " + offlineResult);
        }
    }
}
