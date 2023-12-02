package com.example.a327finalprojectcalorietracker;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class Add_Meal extends AppCompatActivity {
    String api_key;

    //these two should increase the progress bar on the home page as the user
    //click on what food they're entering from the search bar
    private double calculateConsumedCalories(labelNutrients nutritionalInfo){
        //Calculate consumed calories based on the nutritional info received
        double calories = nutritionalInfo.getCalories();

        return calories;
    }

    private void updateProgressBar(double consumedCalories){
        double maxCalories = 2000;

        int progress=(int) ((consumedCalories/maxCalories)*100);

        updateProgressBar(progress);
    }

    //Still need to figure out how this will change the progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //Set up the retrofit to make API calls
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nal.usda.gov/fdc/v1/") // Replace with the actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodSearchService foodSearchService = retrofit.create(FoodSearchService.class);

        // Make sure the SearchView is able to Read Queries
        SearchView searchView = findViewById(R.id.searchViewButton);
        ListView listView = findViewById(R.id.listView);
        //Just added this, not sure if this will intefere with the one that's already defined inside one of the other override fields

        // Set an OnQueryTextListener to the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit (String query){
                // This method is called when the user submits the query (presses Enter)
                Log.d("SearchView", "Query submitted: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange (String newText){
                // This method is called when the text in the search view changes
                Log.d("SearchView", "Query text changed: " + newText);

                Call<APIResponse> call = foodSearchService.getBrandedFoodItem(newText, api_key);
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<foodItem> foodItems = response.body().getFoods();
                            if (foodItems != null && !foodItems.isEmpty()) {

                                List<String> descriptions = new ArrayList<>();
                                for (int i = 0; i < 10 && i < foodItems.size(); i++) {
                                    foodItem foodItemData = foodItems.get(i);
                                    descriptions.add(foodItemData.getDescription());
                                }

                                // Create an ArrayAdapter and set it on the ListView
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_Meal.this,
                                        android.R.layout.simple_list_item_1, descriptions);
                                ListView listView = findViewById(R.id.listView);
                                listView.setAdapter(adapter);
                            } else {
                                // Handle the case when no food items are found
                                Toast.makeText(Add_Meal.this, "No results found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle the case when the API call is not successful
                            Toast.makeText(Add_Meal.this, "Error in API call", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        // Handle the case when the API call fails
                        Toast.makeText(Add_Meal.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });



                return true;
            }
        });
    }

//    private void performFoodSearch(String query) {
//        Call<foodItem> call = FoodSearchService.getBrandedFoodItem(query, "your_api_key");
//        call.enqueue(new Callback<APIResponse>() {
//            @Override
//            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<foodItem> foodItems = response.body().getFoods();
//                    if (foodItems != null && !foodItems.isEmpty()) {
//                        // For simplicity, let's just take the first food item
//                        foodItem firstFoodItem = foodItems.get(0);
//                        labelNutrients nutrients = firstFoodItem.getLabelNutrients();
//                        // Now you can use the 'nutrients' object to access the label nutrients data
//                        // Accessing nutrient values
//                        double fat = nutrients.getFat();
//                        double saturatedFat = nutrients.getSaturatedFat();
//                        double transFat = nutrients.getTransFat();
//                        double cholesterol = nutrients.getCholesterol();
//                        double sodium = nutrients.getSodium();
//                        double carbohydrates = nutrients.getCarbohydrates();
//                        double fiber = nutrients.getFiber();
//                        double sugars = nutrients.getSugars();
//                        double protein = nutrients.getProtein();
//                        double calcium = nutrients.getCalcium();
//                        double iron = nutrients.getIron();
//                        double potassium = nutrients.getPotassium();
//                        double calories = nutrients.getCalories();
//
//                        //Print them in the console
//                        printNutrientValues(fat, saturatedFat, transFat, cholesterol, sodium, carbohydrates,
//                                fiber, sugars, protein, calcium, iron, potassium, calories);
//                    } else {
//                        // Handle the case when no food items are found
//                        Toast.makeText(Add_Meal.this, "No results found", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Handle the case when the API call is not successful
//                    Toast.makeText(Add_Meal.this, "Error in API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<APIResponse> call, Throwable t) {
//                // Handle the case when the API call fails
//                Toast.makeText(Add_Meal.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void printNutrientValues(double fat, double saturatedFat, double transFat, double cholesterol,
                                     double sodium, double carbohydrates, double fiber, double sugars,
                                     double protein, double calcium, double iron, double potassium,
                                     double calories) {
        // Example: Print nutrient values to the console
        System.out.println("Nutrient Values:");
        System.out.println("Fat: " + fat + " grams");
        System.out.println("Saturated Fat: " + saturatedFat + " grams");
        System.out.println("Trans Fat: " + transFat + " grams");
        System.out.println("Cholesterol: " + cholesterol + " milligrams");
        System.out.println("Sodium: " + sodium + " milligrams");
        System.out.println("Carbohydrates: " + carbohydrates + " grams");
        System.out.println("Fiber: " + fiber + " grams");
        System.out.println("Sugars: " + sugars + " grams");
        System.out.println("Protein: " + protein + " grams");
        System.out.println("Calcium: " + calcium + " milligrams");
        System.out.println("Iron: " + iron + " milligrams");
        System.out.println("Potassium: " + potassium + " milligrams");
        System.out.println("Calories: " + calories + " calories");
    }

    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Make an API call to retrieve nutritional information for the consumed food

}
