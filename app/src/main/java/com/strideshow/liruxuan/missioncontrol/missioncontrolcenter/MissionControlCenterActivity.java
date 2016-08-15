package com.strideshow.liruxuan.missioncontrol.missioncontrolcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.strideshow.liruxuan.projectslider.SlideContainerFragment;
import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 8/11/2016.
 */
public class MissionControlCenterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_control_center_fragment);

        SlideContainerFragment slideContainer = new SlideContainerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.slide_container, slideContainer).commit();
    }
}
