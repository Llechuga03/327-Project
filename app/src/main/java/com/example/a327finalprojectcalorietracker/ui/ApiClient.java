package com.example.a327finalprojectcalorietracker.ui;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/"; // Replace with the actual base URL

    private static Retrofit retrofit = null;

    public static Api_Requests getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Api_Requests.class);
    }
}
