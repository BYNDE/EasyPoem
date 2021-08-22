package com.example.easypoem.HttpClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpClientApi {
    @GET("api/poems/title/{title}")
    Call<List<PoemModel>> Search(@Path("title") String title);
}
