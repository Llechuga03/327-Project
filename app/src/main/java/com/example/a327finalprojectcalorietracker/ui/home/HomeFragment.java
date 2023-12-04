package com.example.a327finalprojectcalorietracker.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private Handler handler;
    private ProgressBar progressBarProtein;
    private ProgressBar progressBarCarbs;
    private ProgressBar progressBarFats;
    private FragmentHomeBinding binding;
    private EditText editTextNumber;
    
    private float inNum = 0;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        progressBarCarbs = root.findViewById(R.id.progress_bar_protein);
        progressBarFats = root.findViewById(R.id.progress_bar_fat);
        progressBarProtein = root.findViewById(R.id.progress_bar_carbs);
        int initialProgress=0;
        updateProgress(initialProgress,1);

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

        return root;
    }

    private void updateProgressBars() {

        long delayMillis = 50;

        Runnable updateProgressRunnable2 = new Runnable(){
            @Override
            public void run(){
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
                updateProgress((int)totalConsumedCarbs, 300);
                updateProgress((int)totalConsumedFats,70);
                updateProgress((int)totalConsumedProtein,100);
                //progressBarCarbs.setProgress(progressCarbs);
                //progressBarProtein.setProgress(progressProtein);
                //progressBarFats.setProgress(progressFats);

                // Update percentage TextViews if available in your XML
                TextView textViewProgressProtein = binding.textViewProgressProtein;
                textViewProgressProtein.setText(progressProtein + "%");

                TextView textViewProgressCarbs = binding.textViewProgressCarbs;
                textViewProgressCarbs.setText(progressCarbs + "%");

                TextView textViewProgressFats = binding.textViewProgressFat;
                textViewProgressFats.setText(progressFats + "%");

                handler.postDelayed(this, delayMillis);
            }
        };

        handler.postDelayed(updateProgressRunnable2, delayMillis);
    }

    private void updateProgress(int anotherValue, int maxInput) {
      //Calculate progress based on another value, needs adjustment
        int progress = (int) ((float) anotherValue / maxInput *100);

        progressBarProtein.setProgress(progress);
        progressBarFats.setProgress(progress);
        progressBarCarbs.setProgress(progress);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
