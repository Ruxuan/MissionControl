package com.strideshow.liruxuan.ApiClient;

import retrofit2.Retrofit;

/**
 * Created by Ruxuan on 8/9/2016.
 */
public class ApiClient {
    public static final String BASE_URL = "http://1bccb136.ngrok.io/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }
}
