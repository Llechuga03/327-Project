package com.example.a327finalprojectcalorietracker;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class UserFoods {
    private List<foodItem> foodItems;
    private List<FoodNutrient> totalNutrients;

    public UserFoods() {
        this.foodItems = new ArrayList<>();
        this.totalNutrients = new ArrayList<>();
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
}

