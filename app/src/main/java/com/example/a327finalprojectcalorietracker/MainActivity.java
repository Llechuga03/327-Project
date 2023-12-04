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
                R.id.navigation_home, R.id.navigation_dashboard)
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


    public void goToSecondActivity(View view) {
        Intent intent = new Intent(this, Add_Meal.class);
        startActivity(intent);
    }
}