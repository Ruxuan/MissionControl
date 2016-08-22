package com.strideshow.liruxuan.missioncontrol;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.strideshow.liruxuan.missioncontrol.missioncontrolcenter.MissionControlCenterActivity;
import com.strideshow.liruxuan.projectsgrid.GridFragment;
import com.strideshow.liruxuan.projectslider.SlideContainerFragment;

/**
 * Created by liruxuan on 2016-08-21.
 */
public class MissionControlAdapter extends FragmentPagerAdapter {

    public MissionControlAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new GridFragment();
            case 1:
                return new MissionControlCenterActivity();
            default:
                return null;
        }
    }
}
