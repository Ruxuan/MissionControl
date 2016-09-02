package com.strideshow.liruxuan.projectslider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.strideshow.liruxuan.projectslider.SlideItemFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    private JSONObject data = null;

    public SlidePagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        this.data = data;

        // Create http req here to get slideshow json
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

        SlideItemFragment slideItemFragment = new SlideItemFragment();
        slideItemFragment.setArguments(args);

        return slideItemFragment;
    }
}
