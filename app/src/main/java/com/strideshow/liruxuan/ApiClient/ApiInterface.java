package com.strideshow.liruxuan.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ruxuan on 8/9/2016.
 */
public interface ApiInterface {
    @GET("/api")
    Call<ResponseBody> getProjects();
}
