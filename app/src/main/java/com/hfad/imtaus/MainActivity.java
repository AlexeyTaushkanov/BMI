package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String finagling;
    EditText growthEditText;
    EditText weightEditText;
    TextView outputDataTextView;
    String textIMT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        growthEditText = (EditText) findViewById(R.id.growthEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        outputDataTextView = (TextView) findViewById(R.id.outputDataTextView);

    }

    public static double rounding (double value, int degree) {
        double scale = Math.pow(10, degree);
        return Math.ceil(value * scale) / scale;
    }

    private String getIMT(double imt) {
        String textIMT;
        if (imt < 18.5) {
            textIMT = getString(R.string.underWeight);
        } else if ((imt >= 18.5) && (imt <= 25)) {
            textIMT = getString(R.string.normalWeight);
        } else if ((imt > 25) && (imt < 30)) {
            textIMT = getString(R.string.preObesity);
        } else if ((imt >= 30) && (imt < 35)) {
            textIMT = getString(R.string.obesityClass1);
        } else if ((imt >= 35) && (imt < 40)) {
            textIMT = getString(R.string.obesityClass2);
        } else {
            textIMT = getString(R.string.obesityClass3);
        }
        return textIMT;
    }

    public void onClickFindBeer(View view) {

        if (growthEditText.getText().toString().length() == 0 && weightEditText.getText().toString().length() == 0){
            growthEditText.setError(getString(R.string.enterGrowth));
            weightEditText.setError(getString(R.string.enterWeight));
        } else if (weightEditText.getText().toString().length() == 0) {
            weightEditText.setError(getString(R.string.enterWeight));
        } else if (growthEditText.getText().toString().length() == 0) {
            growthEditText.setError(getString(R.string.enterGrowth));
        } else {
            String rost1 = growthEditText.getText().toString();
            double rost2 = Double.parseDouble(rost1);

            String mass1 = weightEditText.getText().toString();
            double mass2 = Double.parseDouble(mass1);

            TextView outputDataTextView = (TextView) findViewById(R.id.outputDataTextView);

            DecimalFormat form = new DecimalFormat(".#");

            double rost3 = rost2/100;

            double imt = rounding((mass2 / (rost3 * rost3)), 1);

            double minMass = (18.5*rost3*rost3);
            double maxMass = (25*rost3*rost3);

           // Log.d("Progress changed: ", "" + imt + " fin " + minMass);

            textIMT = getIMT(imt);

            finagling = ("Индекс массы тела: " + imt + " - " + textIMT + "\nНормальный вес при росте " + form.format(rost2) + " см должен находится " +
                    "в пределах от " + form.format(minMass) + " до " + form.format(maxMass) + " кг");

            outputDataTextView.setText(finagling);

            }
        }

    public void onClickClear(View view) {

        growthEditText.getText().clear();

        weightEditText.getText().clear();

        outputDataTextView.setText("");

    }

}