package com.example.a327finalprojectcalorietracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a327finalprojectcalorietracker.R;
import com.example.a327finalprojectcalorietracker.databinding.FragmentHomeBinding;import android.os.Handler;
import android.os.Looper;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProgressBar progressBar;
    private Handler handler;
    private TextView foodCal;
    private TextView budgetCal;
    private TextView remainingCal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        foodCal = root.findViewById(R.id.foodCal);
        budgetCal = root.findViewById(R.id.budgetCal);
        remainingCal = root.findViewById(R.id.remainingCal);




        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        handler = new Handler(Looper.getMainLooper());
        progressBar = root.findViewById(R.id.progressBar);
        int initialProgress = 0;
        updateProgress(initialProgress, 1);

        startUpdatingProgress();


        TextView addFoodView = new TextView(getContext());
        addFoodView.setText("New TextView");
        Button mealButton = root.findViewById(R.id.add_meal_button);
        if (mealButton != null) {

            mealButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create and add a new TextView dynamically
                    TextView dynamicTextView = new TextView(requireContext());
                    dynamicTextView.setText("1");

                    // Set layout parameters if needed
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    dynamicTextView.setLayoutParams(layoutParams);

                    // Add the view to the container
                    LinearLayout dynamicContainer = root.findViewById(R.id.dynamicContainer);
                    dynamicContainer.addView(dynamicTextView);
                }
            });
        }

        return root;
    }

    private int getFoodCal(int addCal, int currentCal){
        return addCal + currentCal;
    }
    private int getRemainingCal(int foodIn) {
        return 2000 - foodIn;
    }
    private void startUpdatingProgress() {
        // Define a delay in milliseconds between updates
        long delayMillis = 500; // 1 second, adjust as needed

        // Runnable to update progress
        Runnable updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                // Get the new value that affects progress (replace this with your logic)

                int foodCalVal = 0;

                foodCalVal = 1500; //getFoodCal(nutritionalInfo.getCalories(), foodCalVal);
                foodCal.setText(String.valueOf(foodCalVal));

                int budgetCalVal = 2000;
                budgetCal.setText(String.valueOf(budgetCalVal));

                int remainingCalVal = getRemainingCal(foodCalVal);
                remainingCal.setText(String.valueOf(remainingCalVal));

                int updatedValue = foodCalVal;
                int maxCal = getMaxCal();

                // Update progress based on the new value
                updateProgress(updatedValue, maxCal);

                // Schedule the next update
                handler.postDelayed(this, delayMillis);
            }
        };

        // Schedule the initial update
        handler.postDelayed(updateProgressRunnable, delayMillis);
    }


   /* private int getUpdatedValue() {

        return foodCalVal;
    }
*/

    private int getMaxCal()
    {
        return 2000;
    }

    private void updateProgress(int anotherValue, int maxInput) {
        // Calculate progress based on another value (adjust this calculation based on your needs)
        int progress = (int) ((float) anotherValue / maxInput  * 100);

        // Set the progress to the ProgressBar
        progressBar.setProgress(progress);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}