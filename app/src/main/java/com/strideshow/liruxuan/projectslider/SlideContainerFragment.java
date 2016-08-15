package com.strideshow.liruxuan.projectslider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlideContainerFragment extends Fragment {

    SlidePagerAdapter slidePagerAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slide_container, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        slidePagerAdapter   = new SlidePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);

        viewPager.addOnPageChangeListener(new SlidePagerPageChangeListener());


        return view;
    }
}
