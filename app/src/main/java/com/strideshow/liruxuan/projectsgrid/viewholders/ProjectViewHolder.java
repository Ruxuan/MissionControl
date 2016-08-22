package com.strideshow.liruxuan.projectsgrid.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrolcenter.R;

/**
 * Created by Ruxuan on 8/13/2016.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder {

    private CardView mCardView;
    private TextView mTextView;

    public ProjectViewHolder(View v) {
        super(v);
        mCardView = (CardView) v;
        mTextView = (TextView) v.findViewById(R.id.info_text);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(String text) {
        mTextView.setText(text);
    }


}
