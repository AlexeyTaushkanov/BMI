package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.Precision;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /*public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(v.getId() == R.id.rost)
        {
            if(KeyEvent.KEYCODE_ENTER == keyCode)
            {
                if(rost1.getText().toString().equals("") || rost1.getText().toString().equals(null))
                {
                    imt.setText("Что то пошло не так");
                    //Toast.makeText(KeyboardTestActivity.this, "Event Eaten", Toast.LENGTH_SHORT).show();
                    Log.d("onKey", "Event Eaten");
                    return true;
                }
            }
        }
        return false;
    }*/

    public static double ocrug (double value, int stepen) {
        double scale = Math.pow(10, stepen);
        double valuefin = Math.ceil(value * scale) / scale;
        return valuefin;
    }

    public void onClickFindBeer(View view) {

        String interp;

        EditText growth = (EditText) findViewById(R.id.rost);
        String rost1 = growth.getText().toString();
        double rost2 = Double.parseDouble(rost1);

        EditText weight = (EditText) findViewById(R.id.mass);
        String mass1 = weight.getText().toString();
        double mass2 = Double.parseDouble(mass1);

        TextView imt = (TextView) findViewById(R.id.imt);

        DecimalFormat df = new DecimalFormat("##.#");
        DecimalFormat form = new DecimalFormat("###.#");

        double rost3 = rost2/100;

        double fin0 = (mass2 / (rost3 * rost3));
        double fin = ocrug(fin0, 1);

        double minMass = (18.5*rost3*rost3);
        double maxMass = (25*rost3*rost3);

        Log.d("Progress changed: ", "" + fin + " fin " + minMass);

        interp = getInterp(fin);

        imt.setText("Индекс массы тела: " + fin + " - " + interp + "\nНормальный вес при росте " + form.format(rost2) + " см должен находится " +
                "в пределах от " + form.format(minMass) + " до " + form.format(maxMass) + " кг");


    }

    private String getInterp(double fin) {
        String interp;
        if (fin < 18.5) {
            interp = "Ниже нормального веса";
        } else if ((fin >= 18.5) && (fin <= 25)) {
            interp = "Нормальный вес";
        } else if ((fin > 25) && (fin < 30)) {
            interp = "Избыточный вес";
        } else if ((fin >= 30) && (fin < 35)) {
            interp = "Ожирение | степени";
        } else if ((fin >= 35) && (fin < 40)) {
            interp = "Ожирение || степени";
        } else {
            interp = "Ожирение ||| степени";
        }
        return interp;
    }

    public void onClickClear(View view) {

        EditText growth = (EditText) findViewById(R.id.rost);
        growth.getText().clear();

        EditText weight = (EditText) findViewById(R.id.mass);
        weight.getText().clear();

        TextView imt = (TextView) findViewById(R.id.imt);
        imt.setText("");

    }

  /*  int l = growth.getText().toString().trim().length();
    .setClickable(l == 0 ? false: true);*/

}