package com.example.a327finalprojectcalorietracker;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class UserFoods {

    //singleton instance
    private static UserFoods instance;
    private List<foodItem> foodItems;
    private List<FoodNutrient> totalNutrients;

    //made private for use with singleton constructor
    private UserFoods() {
        //added first line for singleton
        foodItems = new ArrayList<>();
        this.foodItems = new ArrayList<>();
        this.totalNutrients = new ArrayList<>();
    }

    public static UserFoods getInstance() {
        if (instance == null) {
            instance = new UserFoods();
        }
        return instance;
    }

    public void addFood(foodItem food) {
        this.foodItems.add(food);
        this.totalNutrients.addAll(food.getFoodNutrients());
    }

    public void removeFood(foodItem food) {
        if (this.foodItems.remove(food)) {
            this.totalNutrients.removeAll(food.getFoodNutrients());
        }
    }

    public List<foodItem> getFoodItems() {
        return foodItems;
    }

    public List<FoodNutrient> getTotalNutrients() {
        return totalNutrients;
    }

    // Summing up the total carbohydrates
    public float sumTotalCarbs() {
        float totalCarbs = 0;
        for (foodItem food : foodItems) {
            totalCarbs += food.getCarbs();
        }
        return totalCarbs;
    }

    // Summing up the total protein
    public float sumTotalProtein() {
        float totalProtein = 0;
        for (foodItem food : foodItems) {
            totalProtein += food.getProtein();
        }
        return totalProtein;
    }

    // Summing up the total fats
    public float sumTotalFats() {
        float totalFats = 0;
        for (foodItem food : foodItems) {
            totalFats += food.getFat();
        }
        return totalFats;
    }

    // Summing up the total calories
    public float sumTotalCalories() {
        float totalCalories = 0;
        for (foodItem food : foodItems) {
            totalCalories += food.getCalories();
        }
        return totalCalories;
    }
}

