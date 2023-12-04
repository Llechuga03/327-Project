package com.example.a327finalprojectcalorietracker.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a327finalprojectcalorietracker.R;
import com.example.a327finalprojectcalorietracker.UserFoods;
import com.example.a327finalprojectcalorietracker.databinding.FragmentHomeBinding;
import com.example.a327finalprojectcalorietracker.foodItem;
import com.example.a327finalprojectcalorietracker.sharedView;


public class HomeFragment extends Fragment {

    private sharedView sharedViewModel;
    private ProgressBar progressBarProtein;
    private ProgressBar progressBarCarbs;
    private Handler handler;
    private ProgressBar progressBarFats;
    private FragmentHomeBinding binding;
    private EditText editTextNumber;
    private TextView textViewProgressProtein;
    private TextView textViewProgressCarbs;
    private TextView textViewProgressFats;


    private float inNum = 0;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBarCarbs = binding.progressBarCarbs;
        progressBarFats = binding.progressBarFat;
        progressBarProtein = binding.progressBarProtein;

        editTextNumber = binding.editTextNumber;


        final TextView textHome = binding.textHome;
        // Set date text here (if applicable)

        // Initialize and update progress bars
        updateProgressBars();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(sharedView.class);

        editTextNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Retrieve the value from the EditText
                    String valueStr = editTextNumber.getText().toString();

                    if (!TextUtils.isEmpty(valueStr)) {
                        float enteredValue = Float.parseFloat(valueStr);

                        sharedViewModel.setBudgetCalVal(enteredValue);
                        editTextNumber.setText("");
                    }

                    return true;
                }
                return false;
            }
        });

        if (handler == null) {
            handler = new Handler();
            startUpdatingProgress();
        }

        return root;
    }


    private void startUpdatingProgress() {




        long delayMillis = 50;




        Runnable updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                // need to update with proper logic

                // Update TextViews


                // Update progress based on the new value
                updateProgressBars();





                handler.postDelayed(this, delayMillis);
            }
        };




        handler.postDelayed(updateProgressRunnable, delayMillis);
    }



    private void updateProgressBars() {
        // Get total consumed nutrients
        float totalConsumedCarbs = UserFoods.getInstance().sumTotalCarbs();
        float totalConsumedProtein = UserFoods.getInstance().sumTotalProtein();
        float totalConsumedFats = UserFoods.getInstance().sumTotalFats();

        // Calculate percentages
        float maxCarbs = 300;
        float maxProtein = 100;
        float maxFats = 70;

        int progressCarbs = (int) ((totalConsumedCarbs / maxCarbs) * 100);
        int progressProtein = (int) ((totalConsumedProtein / maxProtein) * 100);
        int progressFats = (int) ((totalConsumedFats / maxFats) * 100);

        // Update progress bars
        progressBarCarbs.setProgress(progressCarbs);
        progressBarProtein.setProgress(progressProtein);
        progressBarFats.setProgress(progressFats);

        // Update percentage TextViews if available in your XML
        if (binding != null) {
            textViewProgressProtein = binding.textViewProgressProtein;

            if (textViewProgressProtein != null) {
                textViewProgressProtein.setText(progressProtein + "%");
            }

            textViewProgressCarbs = binding.textViewProgressCarbs;
            if (textViewProgressCarbs != null) {
                textViewProgressCarbs.setText(progressCarbs + "%");
            }

            textViewProgressFats = binding.textViewProgressFat;
            if (textViewProgressFats != null) {
                textViewProgressFats.setText(progressFats + "%");
            }
        }
    }

    private void addFoodAndUpdateProgress(foodItem desiredFood) {
        UserFoods userFoods = UserFoods.getInstance();
        userFoods.addFood(desiredFood);
        updateProgressBars();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
