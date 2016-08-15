package com.strideshow.liruxuan.missioncontrol;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.strideshow.liruxuan.projectsgrid.GridFragment;
import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 8/10/2016.
 */
public class MissionControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_control_activity);

        // Open the main fragment for mission control
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Mission control grid container is the main fragment
        GridFragment fragment = new GridFragment();
        fragmentTransaction.add(R.id.missionControlActivityView, fragment);
        fragmentTransaction.commit();
    }
}
