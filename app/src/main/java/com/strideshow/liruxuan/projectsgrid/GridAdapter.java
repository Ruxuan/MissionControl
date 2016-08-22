package com.strideshow.liruxuan.projectsgrid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strideshow.liruxuan.missioncontrolcenter.R;
import com.strideshow.liruxuan.projectsgrid.viewholders.FolderViewHolder;
import com.strideshow.liruxuan.projectsgrid.viewholders.ProjectViewHolder;
import com.strideshow.liruxuan.projectsgrid.viewholders.SectionTitleViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruxuan on 8/11/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<RecyclerObject> data = new ArrayList<>();

    public static final int SECTION = 0;
    public static final int FOLDER  = 1;
    public static final int PROJECT = 2;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GridAdapter(JSONArray json) {

        // TODO: temporary folder and section test

        JSONObject folder1 = new JSONObject();
        JSONObject folder2 = new JSONObject();
        JSONObject folder3 = new JSONObject();
        try {
            folder1.put("folderName", "Special projects");
            folder2.put("folderName", "Client projects");
            folder3.put("folderName", "Experimental projects");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data.add(new RecyclerObject(folder1, 1));
        data.add(new RecyclerObject(folder2, 1));
        data.add(new RecyclerObject(folder3, 1));

        JSONObject sectionJSONObject = new JSONObject();
        try {
            sectionJSONObject.put("name", "Projects");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data.add(new RecyclerObject(sectionJSONObject, 0));

        // end TODO

        // Compute appropriate data for recycler view
        for (int i = 0; i < json.length(); i++) {
            // Get Json Object from array
            JSONObject jsonObject = json.optJSONObject(i);
            // Create a new recycler object with it
            RecyclerObject projectObject = new RecyclerObject(jsonObject, 2);
            // Add it to the Recycler Object array
            data.add(projectObject);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SECTION:
                View sectionView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grid_section_title, parent, false);
                return new SectionTitleViewHolder(sectionView);
            case FOLDER:
                View folderView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grid_folder, parent, false);
                return new FolderViewHolder(folderView);
            case PROJECT:
                // Projects
                // create a new view
                View projectView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grid_project, parent, false);

                // set the view's size, margins, paddings and layout parameters
                return new ProjectViewHolder(projectView);
            default:
                // how'd we get here?
                return null;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        JSONObject recyclerJSON = data.get(position).getData();

        // TODO: create generic type to avoid casting?
        switch(holder.getItemViewType()) {
            case SECTION:
                String name = recyclerJSON.optString("name");
                SectionTitleViewHolder stvh = (SectionTitleViewHolder) holder;
                stvh.setTextView(name);
                return;
            case FOLDER:
                String folderName = recyclerJSON.optString("folderName");
                FolderViewHolder fvh = (FolderViewHolder) holder;
                fvh.setTextView(folderName);
                return;
            case PROJECT:
                String title = recyclerJSON.optJSONObject("meta_data").optString("title");
                ProjectViewHolder pvh = (ProjectViewHolder) holder;
                pvh.setTextView(title);
                return;
            default:
                return;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}
