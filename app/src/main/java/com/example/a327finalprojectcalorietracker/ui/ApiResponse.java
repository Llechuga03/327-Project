package com.example.a327finalprojectcalorietracker.ui;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("foods")
    private List<foodItem> foods;

    public List<foodItem> getFoods() {
        return foods;
    }
}


