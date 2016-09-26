package com.strideshow.liruxuan.projectsgrid.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.strideshow.liruxuan.missioncontrol.R;

/**
 * Created by liruxuan on 2016-08-17.
 */
public class FolderViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextView;

    public FolderViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.folderNameView);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(String text) {
        mTextView.setText(text);
    }
}
