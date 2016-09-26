package com.strideshow.liruxuan.projectslider.projectviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    public static final String STEP = "STEP";

    private JSONObject data = null;

    public SlidePagerAdapter(FragmentManager fm, JSONObject projectData) {
        super(fm);
        this.data = projectData;
    }

    @Override
    public int getCount() {
        try {
            return data.getJSONObject("presentation").getInt("length");
        } catch (JSONException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();

        args.putInt("page", position);
        args.putInt("count", getCount());

        JSONObject step;
        try {
            step = data.getJSONObject("presentation")
                    .getJSONArray("steps")
                    .getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            step = new JSONObject();
        }

        args.putString(STEP, step.toString());

        SlideItemFragment slideItemFragment = new SlideItemFragment();
        slideItemFragment.setArguments(args);

        return slideItemFragment;
    }
}
