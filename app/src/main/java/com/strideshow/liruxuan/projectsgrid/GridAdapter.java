package com.strideshow.liruxuan.projectsgrid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strideshow.liruxuan.missioncontrolcenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruxuan on 8/11/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<ProjectViewHolder>{
    private JSONArray mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GridAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProjectViewHolder onCreateViewHolder
        (ViewGroup parent, int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ProjectViewHolder vh = new ProjectViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        JSONObject text = null;
        String title = null;
        try {
            text = mDataset.getJSONObject(position);
            title = text.getJSONObject("meta_data").getString("title");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        holder.mTextView.setText(title);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}
