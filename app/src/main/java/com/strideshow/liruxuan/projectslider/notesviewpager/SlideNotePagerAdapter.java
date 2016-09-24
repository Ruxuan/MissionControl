package com.strideshow.liruxuan.projectslider.notesviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.strideshow.liruxuan.projectslider.SlideNoteItemFragment;

import java.util.ArrayList;

/**
 * Created by liruxuan on 2016-09-14.
 */
public class SlideNotePagerAdapter extends FragmentPagerAdapter {
    public static final String NOTES = "NOTES";

    private ArrayList<String> slideNotes;

    public SlideNotePagerAdapter(FragmentManager fm, ArrayList<String> slideNotes) {
        super(fm);
        this.slideNotes = slideNotes;
    }

    @Override
    public int getCount() {
        return slideNotes.size();
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
