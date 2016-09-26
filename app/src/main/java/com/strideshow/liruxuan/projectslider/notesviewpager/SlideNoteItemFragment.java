package com.strideshow.liruxuan.projectslider.notesviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrol.R;

/**
 * Created by liruxuan on 2016-09-14.
 */

public class SlideNoteItemFragment extends Fragment {

    private String note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.note = getArguments().getString(SlideNotePagerAdapter.NOTES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_note_item_fragment, container, false);

        TextView tv = (TextView) view.findViewById(R.id.slideNoteTextView);
        tv.setText(note);

        return view;
    }
}
