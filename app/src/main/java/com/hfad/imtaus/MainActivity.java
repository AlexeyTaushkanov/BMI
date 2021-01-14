package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String outputTextBMI;
    EditText growthEditText;
    EditText weightEditText;
    TextView outputDataTextView;
    String textIMT;
    double growthCm;
    double massKg;
    double growthMetre;
    double BMI;
    double minBodyMassKg;
    double maxBodyMassKg;

    double minBodyMassCoefficient = 18.5;
    double maxBodyMassCoefficient = 25;
    boolean touchButtonBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        growthEditText = (EditText) findViewById(R.id.growthEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        outputDataTextView = (TextView) findViewById(R.id.outputDataTextView);

        if (savedInstanceState != null) {
            touchButtonBMI = savedInstanceState.getBoolean("touchButtonBMI");
            outputTextBMI = savedInstanceState.getString("outputTextBMI");
        }

        if (touchButtonBMI) {
            outputDataTextView.setText(outputTextBMI);
        }

    }

    DecimalFormat form1 = new DecimalFormat(".#");
    DecimalFormat form2 = new DecimalFormat("#");

    public static double rounding (double value, int afterPoint) {
        double scale = Math.pow(10, afterPoint);
        return Math.ceil(value * scale) / scale;
    }

    private String getBMI(double BMI) {
        String textIMT;
        if (BMI < 18.5) {
            textIMT = getString(R.string.underWeight);
        } else if ((BMI >= 18.5) && (BMI <= 25)) {
            textIMT = getString(R.string.normalWeight);
        } else if ((BMI > 25) && (BMI < 30)) {
            textIMT = getString(R.string.preObesity);
        } else if ((BMI >= 30) && (BMI < 35)) {
            textIMT = getString(R.string.obesityClass1);
        } else if ((BMI >= 35) && (BMI < 40)) {
            textIMT = getString(R.string.obesityClass2);
        } else {
            textIMT = getString(R.string.obesityClass3);
        }
        return textIMT;
    }

    public void onClickFindBMI(View view) {

        touchButtonBMI = true;

        if (growthEditText.getText().toString().length() == 0 && weightEditText.getText().toString().length() == 0){
            growthEditText.setError(getString(R.string.enterGrowth));
            weightEditText.setError(getString(R.string.enterWeight));
        } else if (weightEditText.getText().toString().length() == 0) {
            weightEditText.setError(getString(R.string.enterWeight));
        } else if (growthEditText.getText().toString().length() == 0) {
            growthEditText.setError(getString(R.string.enterGrowth));
        } else {

            growthCm = Double.parseDouble(growthEditText.getText().toString());

            massKg = Double.parseDouble(weightEditText.getText().toString());

            growthMetre = growthCm/100;

            BMI = rounding((massKg / (growthMetre * growthMetre)), 1);

            minBodyMassKg = (minBodyMassCoefficient*growthMetre*growthMetre);
            maxBodyMassKg = (maxBodyMassCoefficient*growthMetre*growthMetre);

          // Log.d("Progress changed: ", "" + BMI + " fin " + minBodyMassKg + "massKg" + massKg + "growthMetre" + growthMetre);

            textIMT = getBMI (BMI);

            outputTextBMI = ("Индекс массы тела: " + BMI + " - " + textIMT + "\nНормальный вес при росте " +
                    form2.format(growthCm) + " см должен находится в пределах от " + form1.format(minBodyMassKg) +
                    " до " + form1.format(maxBodyMassKg) + " кг");

                outputDataTextView.setText(outputTextBMI);

            }
        }

    public void onClickClear(View view) {

        growthEditText.getText().clear();

        weightEditText.getText().clear();

        outputDataTextView.setText("");

        touchButtonBMI = false;

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("touchButtonBMI", touchButtonBMI);
        savedInstanceState.putString("outputTextBMI", outputTextBMI);
    }

}