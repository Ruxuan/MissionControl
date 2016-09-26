package com.strideshow.liruxuan.projectslider.notesviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;

/**
 * Created by liruxuan on 2016-09-14.
 */
public class SlideNotePagerAdapter extends FragmentPagerAdapter {
    public static final String NOTES = "NOTES";

    private JSONArray projectSteps;

    public SlideNotePagerAdapter(FragmentManager fm, JSONArray projectSteps) {
        super(fm);
        this.projectSteps = projectSteps;
    }

    @Override
    public int getCount() {
        return projectSteps.length();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();

        // Temp msg
        args.putString(NOTES, "Slide notes for slide " + position);

        SlideNoteItemFragment slideNoteItemFragment = new SlideNoteItemFragment();
        slideNoteItemFragment.setArguments(args);

        return slideNoteItemFragment;
    }
}
