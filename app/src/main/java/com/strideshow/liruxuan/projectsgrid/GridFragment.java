package com.strideshow.liruxuan.projectsgrid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strideshow.liruxuan.ApiClient.ApiClient;
import com.strideshow.liruxuan.ApiClient.ApiInterface;
import com.strideshow.liruxuan.missioncontrolcenter.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ruxuan on 8/12/2016.
 */
public class GridFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment, container, false);

        // Get recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gridRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getProjects();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String body = response.body().string();
                        JSONArray projects = new JSONArray(body);

                        mAdapter = new GridAdapter(projects);
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (IOException e) {
                        System.out.println("fucked up");
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    } catch (JSONException e) {
                        System.out.println("Could not parse json");
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        // TODO: recycler view grid manage

        return view;
    }
}
