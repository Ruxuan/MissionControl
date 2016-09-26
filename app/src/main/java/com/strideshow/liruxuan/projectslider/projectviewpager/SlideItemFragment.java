package com.strideshow.liruxuan.projectslider.projectviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrol.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 6/27/2016.
 */
public class SlideItemFragment extends Fragment {

    private String title;
    private int page;
    private int count;

    private JSONObject stepData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page  = getArguments().getInt("page");
        count = getArguments().getInt("count");

        try {
            stepData = new JSONObject(getArguments().getString(SlidePagerAdapter.STEP));
        } catch (JSONException e) {
            e.printStackTrace();
            stepData = new JSONObject();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_item, container, false);

        TextView tv = (TextView) view.findViewById(R.id.index);
        tv.setText(page + " / " + count + " : " + stepData.optString("title"));

        WebView wv = (WebView) view.findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);

        //wv.getSettings().setLoadWithOverviewMode(true);
        //wv.getSettings().setUseWideViewPort(true);

        wv.loadData(stepData.optString("html"), "text/html; charset=utf-8", "UTF-8");

        return view;
    }
}
