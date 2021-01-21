package com.hfad.imtaus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MoreActivity extends AppCompatActivity implements MoreFragment.onSomeEventListener {

    /*Fragment moreFragment;
    FragmentTransaction fTrans;*/

    androidx.fragment.app.Fragment aboutUs = new AboutUsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
    }


    @Override
    public void someEvent() {
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frgmCont, aboutUs);
        ft.commit();
    }

    /*public void onClick(View v) {
        fTrans = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.more_fragment:
                fTrans.replace(R.id.frgmCont, moreFragment);
                break;
            default:
                break;
        }
        fTrans.commit();
    }*/

}