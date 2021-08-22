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
                .baseUrl("http://ef6d-185-210-140-195.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpClientApi = retrofit.create(HttpClientApi.class);
    }

    public static HttpClientApi getApi() {
        return httpClientApi;
    }
}
