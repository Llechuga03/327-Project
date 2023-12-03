package com.example.a327finalprojectcalorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
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
    String api_key = "fMoh8eI1l06bnM1emwJx5DWxDU9AwY3AMNHfXeSp";

    //these two should increase the progress bar on the home page as the user
    //click on what food they're entering from the search bar
    private double calculateConsumedCalories(labelNutrients nutritionalInfo) {
        //Calculate consumed calories based on the nutritional info received
        double calories = nutritionalInfo.getCalories();

        return calories;
    }

    private void updateProgressBar(double consumedCalories) {
        double maxCalories = 2000;

        int progress = (int) ((consumedCalories / maxCalories) * 100);

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
        // Set an OnQueryTextListener to the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This method is called when the user submits the query (presses Enter)
                Log.d("SearchView", "Query submitted: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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

        // Set the onItemClickListener
        UserFoods userFoods = new UserFoods();

        ListView listView = findViewById(R.id.listView);

        List<foodItem> foodItems = userFoods.getFoodItems();

        ArrayAdapter<foodItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodItems);

        listView.setAdapter(adapter);

        //FIXME appending information makes app crash @line 118 || parent.getItemAtPosition(position) is returning a String
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from ListView
                foodItem selectedItem = (foodItem) parent.getItemAtPosition(position);

                // Check if the selected item is not null
                if (selectedItem != null) {
                    // Call addFood on the instance of UserFoods
                    userFoods.addFood(selectedItem);

                    // Log the updated UserFoods
                    Log.d("UserFoods", "Updated UserFoods: " + userFoods.toString());
                    //Calculate consumed calories here based on the selected item's nutritonal info
                    double consumedCalories = calculateConsumedCalories(selectedItem.getLabelNutrients());

                    //create an Intent to send progress values back to MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("consumedCalories",consumedCalories);
                    setResult(RESULT_OK, resultIntent);
                    finish(); //Finish Add_Meal activity and return to MainActivity
                } else {
                    Log.e("UserFoods", "Selected food item is null");
                }
            }
        });
    }


        public void goToMainMenu (View view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Make an API call to retrieve nutritional information for the consumed food
}

