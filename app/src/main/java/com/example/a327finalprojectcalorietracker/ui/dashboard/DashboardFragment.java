package com.example.a327finalprojectcalorietracker.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.a327finalprojectcalorietracker.R;
import com.example.a327finalprojectcalorietracker.UserFoods;
import com.example.a327finalprojectcalorietracker.databinding.FragmentDashboardBinding;
import com.example.a327finalprojectcalorietracker.foodItem;


import android.os.Handler;
import android.os.Looper;




public class DashboardFragment extends Fragment {


    private FragmentDashboardBinding binding;
    private ProgressBar progressBar;
    private Handler handler;
    private TextView foodCal;
    private TextView budgetCal;
    private TextView remainingCal;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        foodCal = root.findViewById(R.id.foodCal);
        budgetCal = root.findViewById(R.id.budgetCal);
        remainingCal = root.findViewById(R.id.remainingCal);


        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        handler = new Handler(Looper.getMainLooper());
        progressBar = root.findViewById(R.id.progressBar);
        int initialProgress = 0;
        updateProgress(initialProgress, 1);




        startUpdatingProgress();


        return root;
    }


    double foodCalVal = 0;
    double budgetCalVal = 2000;


    private void startUpdatingProgress() {




        long delayMillis = 50;




        Runnable updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                // need to update with proper logic

                
                foodCalVal  = UserFoods.getInstance().sumTotalCalories();
                

                generateFoodTextViews();
                

                // Update TextViews
                foodCal.setText(String.valueOf(foodCalVal));
                budgetCal.setText(String.valueOf(budgetCalVal));
                double remainingCalVal = budgetCalVal - foodCalVal;
                remainingCal.setText(String.valueOf(remainingCalVal));




                // Update progress based on the new value
                updateProgress((int)foodCalVal, (int)budgetCalVal);






                handler.postDelayed(this, delayMillis);
            }
        };




        handler.postDelayed(updateProgressRunnable, delayMillis);
    }


    private void generateFoodTextViews() {
        LinearLayout foodsContainer = requireView().findViewById(R.id.foodsContainer);

        foodsContainer.removeAllViews();


        for (foodItem food : UserFoods.getInstance().getFoodItems()) {
            // Create a new TextView
            TextView foodTextView = new TextView(requireContext());
            foodTextView.setText(" " + food.getDescription() + ": " + food.getCalories() + " calories");
            foodTextView.setBackgroundResource(R.drawable.background_square3);
            foodTextView.setTextSize(16);
            foodTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            foodsContainer.addView(foodTextView);
        }
    }


    private void updateProgress(int anotherValue, int maxInput) {
        // Calculate progress based on another value , needs adjustment
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
