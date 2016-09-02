package com.strideshow.liruxuan.projectslider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlideItemFragment extends Fragment {

    private String title;
    private int page;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page  = getArguments().getInt("page");
        count = getArguments().getInt("count");
        //title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_item, container, false);

        TextView tv = (TextView) view.findViewById(R.id.index);
        tv.setText("Data: " + page + " -- " + count);

        return view;
    }
}
