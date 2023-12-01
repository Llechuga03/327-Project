package com.example.a327finalprojectcalorietracker.ui;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api_Requests {
    @GET("foods/search")
    Call<ApiResponse> searchFoods(@Query("query") String query, @Query("apiKey") String apiKey);
}
