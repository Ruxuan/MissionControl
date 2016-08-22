package com.strideshow.liruxuan.projectsgrid.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by liruxuan on 2016-08-17.
 */
public class SectionTitleViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public SectionTitleViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.sectionTitleView);
    }

    public void setTextView(String text) {
        mTextView.setText(text);
    }
}
