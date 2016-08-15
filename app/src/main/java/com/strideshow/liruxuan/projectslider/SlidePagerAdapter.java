package com.strideshow.liruxuan.projectslider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.strideshow.liruxuan.projectslider.SlideItemFragment;

import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    private final static int ITEM_COUNT = 20;

    private JSONObject slideshow;

    public SlidePagerAdapter(FragmentManager fm) {
        super(fm);


        // Create http req here to get slideshow json
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("page", position);
        SlideItemFragment slideItemFragment = new SlideItemFragment();

        switch (position) {
            case 0:
                args.putString("title", "FIRST SLIDE");
                break;
            case 1:
                args.putString("title", "SECOND SLIDE");
                break;
            case 2:
                args.putString("title", "THIRD SLIDE");
                break;
            default:
                args.putString("title", "WATER FAK");
                break;
        }

        slideItemFragment.setArguments(args);

        return slideItemFragment;
    }
}
