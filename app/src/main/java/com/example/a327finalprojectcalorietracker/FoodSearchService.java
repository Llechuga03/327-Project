package com.example.a327finalprojectcalorietracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodSearchService {
    @GET("foods/search")
    Call<APIResponse> getBrandedFoodItem(@Query("query") String query, @Query("api_key") String apiKey);
}
