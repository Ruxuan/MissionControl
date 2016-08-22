package com.strideshow.liruxuan.missioncontrol.missioncontrolcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strideshow.liruxuan.projectslider.SlideContainerFragment;
import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 8/11/2016.
 */
public class MissionControlCenterActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mission_control_center_fragment, container, false);

        SlideContainerFragment slideContainer = new SlideContainerFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.slide_container, slideContainer).commit();

        return view;
    }
}
