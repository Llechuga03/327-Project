package com.example.a327finalprojectcalorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a327finalprojectcalorietracker.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    Button add_meal;
    private ActivityMainBinding binding;

    private static final int ADD_MEAL_REQUEST_CODE = 1; //Define a request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        add_meal = findViewById(R.id.add_meal_button);
        add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Meal.class);
                startActivityForResult(intent, ADD_MEAL_REQUEST_CODE);
            }
        });
    }


    //This is meant to trigger the updateProgressbarForCalories when theres an action
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MEAL_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null && data.hasExtra("consumedCalories")){
                double consumedCalories = data.getDoubleExtra("consumedCalories",0.0);

                //Update progress bars based on the recieved consumedCalories
                //For example:
                //updateProgressBarForCalories(consumedCalories);
            }
        }
    }

    //this is the code to update each progress bar
    private void updateProgressBars(){
        //float totalConsumedCalories = UserFoods.getInstance().sumTotalCalories();
        float totalConsumedCarbs = UserFoods.getInstance().sumTotalCarbs();
        float totalConsumedProtein = UserFoods.getInstance().sumTotalProtein();
        float totalConsumedFats = UserFoods.getInstance().sumTotalFats();

        //ProgressBar progressBarCalories = findViewById(R.id.progress_bar_calories);
        ProgressBar progressBarProtein = findViewById(R.id.progress_bar_protein);
        ProgressBar progressBarCarbs = findViewById(R.id.progress_bar_carbs);
        ProgressBar progressBarFats = findViewById(R.id.progress_bar_fat);

        // Calculate percentage for each nutrient
        //float maxCalories = 2000; // Assuming 2000 is the max daily calorie intake
        float maxCarbs = 300; // Assuming 300 grams as the max daily carb intake
        float maxProtein = 100; // Assuming 100 grams as the max daily protein intake
        float maxFats = 70; // Assuming 70 grams as the max daily fat intake

        //int progressCalories = (int) ((totalConsumedCalories / maxCalories) * 100);
        int progressCarbs = (int) ((totalConsumedCarbs / maxCarbs) * 100);
        int progressProtein = (int) ((totalConsumedProtein / maxProtein) * 100);
        int progressFats = (int) ((totalConsumedFats / maxFats) * 100);

        // Update progress bars with calculated percentages
        //progressBarCalories.setProgress(progressCalories);
        progressBarCarbs.setProgress(progressCarbs);
        progressBarProtein.setProgress(progressProtein);
        progressBarFats.setProgress(progressFats);
    }
    //we might need to update this based on how we want to increase each progress bar

    private void addFoodAndUpdateProgress(foodItem desiredFood){
        UserFoods userFoods = UserFoods.getInstance();
        userFoods.addFood(desiredFood);
        updateProgressBars();
    }


    public void goToSecondActivity(View view) {
        Intent intent = new Intent(this, Add_Meal.class);
        startActivity(intent);
    }
}