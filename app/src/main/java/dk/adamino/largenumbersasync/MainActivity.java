package dk.adamino.largenumbersasync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

        setupViews();

        mLargeNumberCalculator = new LargeNumberCalculator();
    }

    private void setupViews() {
        mNumber1 = findViewById(R.id.txtNumber1);
        mNumber2 = findViewById(R.id.txtNumber2);
        mResult = findViewById(R.id.txtResult);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void onCalculate(View view) {
        long input1, input2;
        if (hasInput(mNumber1) && hasInput(mNumber2)) {
            input1 = getLong(mNumber1);
            input2 = getLong(mNumber2);

            try {
                mLargeNumberCalculator.addAsync(this, input1, input2);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.animate();
            } catch (Exception oe) {
                Log.e("GUI", oe.getMessage());
            }
        } else {
            Toast.makeText(this, R.string.error_enter_numbers, Toast.LENGTH_SHORT).show();
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
