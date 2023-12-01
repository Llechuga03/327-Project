package com.example.a327finalprojectcalorietracker.ui;

import com.google.gson.annotations.SerializedName;

public class labelNutrients {
    @SerializedName("fat")
    private double fat;

    @SerializedName("saturatedFat")
    private double saturatedFat;

    @SerializedName("transFat")
    private double transFat;

    @SerializedName("cholesterol")
    private double cholesterol;

    @SerializedName("sodium")
    private double sodium;

    @SerializedName("carbohydrates")
    private double carbohydrates;

    @SerializedName("fiber")
    private double fiber;

    @SerializedName("sugars")
    private double sugars;

    @SerializedName("protein")
    private double protein;

    @SerializedName("calcium")
    private double calcium;

    @SerializedName("iron")
    private double iron;

    @SerializedName("potassium")
    private double potassium;

    @SerializedName("calories")
    private double calories;

    // Getter and Setter methods for each nutrient

    public double getFat() {
        return fat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public double getTransFat() {
        return transFat;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public double getSodium() {
        return sodium;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugars() {
        return sugars;
    }

    public double getProtein() {
        return protein;
    }

    public double getCalcium() {
        return calcium;
    }

    public double getIron() {
        return iron;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getCalories() {
        return calories;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}

