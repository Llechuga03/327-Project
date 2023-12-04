package com.example.a327finalprojectcalorietracker.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a327finalprojectcalorietracker.R;
import com.example.a327finalprojectcalorietracker.UserFoods;
import com.example.a327finalprojectcalorietracker.databinding.FragmentDashboardBinding;
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

        ProgressBar progressBar1 = requireActivity().findViewById(R.id.progress_bar_protein);
        ProgressBar progressBar2 = requireActivity().findViewById(R.id.progress_bar_carbs);
        ProgressBar progressBar3 = requireActivity().findViewById(R.id.progress_bar_fat);

        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);
        progressBar3.setVisibility(View.GONE);

        handler = new Handler(Looper.getMainLooper());
        progressBar = root.findViewById(R.id.progressBar);
        int initialProgress = 0;
        updateProgress(initialProgress, 1);


        startUpdatingProgress();

        return root;
    }

    double foodCalVal = 0;
    double budgetCalVal = 0;

    private void startUpdatingProgress() {


        long delayMillis = 50;


        Runnable updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                // need to update with proper logic


                foodCalVal  = UserFoods.getInstance().sumTotalCalories();


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
