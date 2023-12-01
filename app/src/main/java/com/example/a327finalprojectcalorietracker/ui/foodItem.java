package com.example.a327finalprojectcalorietracker.ui;

import com.google.gson.annotations.SerializedName;

public class foodItem {
    @SerializedName("fdcId")
    private long fdcId;

    public long getFdcId() {
        return fdcId;
    }
}
