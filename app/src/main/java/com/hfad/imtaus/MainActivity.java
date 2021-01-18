package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment;
import android.view.View.OnClickListener;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider _shareActionProvider;

    DialogFragment _dlg;

    String _outputTextBMI;
    EditText _growthEditText;
    EditText _weightEditText;
    TextView _outputDataTextView;
    String _textIMT;
    double _growthCm;
    double _massKg;
    double _growthMetre;
    double _BMI;
    double _minBodyMassKg;
    double _maxBodyMassKg;

    double _minBodyMassCoefficient = 18.5;
    double _maxBodyMassCoefficient = 25;
    boolean _touchButtonBMI;

    DecimalFormat _form1 = new DecimalFormat(".#");
    DecimalFormat _form2 = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _growthEditText = (EditText) findViewById(R.id.growthEditText);
        _weightEditText = (EditText) findViewById(R.id.weightEditText);
        _outputDataTextView = (TextView) findViewById(R.id.outputDataTextView);

        if (savedInstanceState != null) {
            _touchButtonBMI = savedInstanceState.getBoolean("_touchButtonBMI");
            _outputTextBMI = savedInstanceState.getString("_outputTextBMI");
        }
        if (_touchButtonBMI) {
            _outputDataTextView.setText(_outputTextBMI);
        }

        _dlg = new Dialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        _shareActionProvider = (androidx.appcompat.widget.ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setIntent("ИМТ Калькулятор App: https://play.google.com/store/apps/details?id=ru.medsoftpro.bmicalc");
        return super.onCreateOptionsMenu(menu);
    }


    /*Intent sendIntent = new Intent();
sendIntent.setAction(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_TEXT, "Приложение name, скачивай от сюда - ссылка");
sendIntent.setType("text/plain");
    startActivity(Intent.createChooser(sendIntent,"Поделиться"));*/

    public void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Intent.createChooser(intent,"Поделиться");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        _shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_info:
                _dlg.show(getFragmentManager(), "dlg");
                return true;
            case R.id.action_history:
                /*openSettings();*/
                return true;
            case R.id.action_more:
                Intent intent = new Intent(this, MoreActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
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

        _touchButtonBMI = true;

        if (_growthEditText.getText().toString().length() == 0 && _weightEditText.getText().toString().length() == 0)
        {
            _growthEditText.setError(getString(R.string.enterGrowth));
            _weightEditText.setError(getString(R.string.enterWeight));
        }
        else if (_weightEditText.getText().toString().length() == 0)
        {
            _weightEditText.setError(getString(R.string.enterWeight));
        }
        else if (_growthEditText.getText().toString().length() == 0)
        {
            _growthEditText.setError(getString(R.string.enterGrowth));
        }
        else {

            _growthCm = Double.parseDouble(_growthEditText.getText().toString());
            _massKg = Double.parseDouble(_weightEditText.getText().toString());

            _growthMetre = _growthCm/100;

            _BMI = rounding((_massKg / (_growthMetre * _growthMetre)), 1);

            _minBodyMassKg = (_minBodyMassCoefficient*_growthMetre*_growthMetre);
            _maxBodyMassKg = (_maxBodyMassCoefficient*_growthMetre*_growthMetre);

          // Log.d("Progress changed: ", "" + BMI + " fin " + minBodyMassKg + "massKg" + massKg + "growthMetre" + growthMetre);

            _textIMT = getBMI (_BMI);

            _outputTextBMI = ("Индекс массы тела: " + _BMI + " - " + _textIMT + "\nНормальный вес при росте " +
                    _form2.format(_growthCm) + " см должен находится в пределах от " + _form1.format(_minBodyMassKg) +
                    " до " + _form1.format(_maxBodyMassKg) + " кг");

                _outputDataTextView.setText(_outputTextBMI);

            }
        }

    public void onClickClear(View view) {
        _growthEditText.getText().clear();
        _weightEditText.getText().clear();
        _outputDataTextView.setText("");
        _touchButtonBMI = false;
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        _shareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }*/


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("_touchButtonBMI", _touchButtonBMI);
        savedInstanceState.putString("_outputTextBMI", _outputTextBMI);
    }
}