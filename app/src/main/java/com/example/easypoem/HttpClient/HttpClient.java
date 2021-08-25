package com.example.easypoem.HttpClient;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient extends Application {

    private static HttpClientApi httpClientApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://34.116.139.139/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpClientApi = retrofit.create(HttpClientApi.class);
    }

    public static HttpClientApi getApi() {
        return httpClientApi;
    }
}
