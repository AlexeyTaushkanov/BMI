package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MoreActivity<fragmentContainer> extends AppCompatActivity implements MoreFragment.onSomeEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
    }

    @Override
    public void someEvent() {
        View fragmentContainer = findViewById(R.id.frgmCont);
        if (fragmentContainer != null) {
            androidx.fragment.app.Fragment aboutUs = new AboutUsFragment();
            androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frgmCont, aboutUs);
            ft.commit();
        } else {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }

    }


}