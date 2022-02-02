package com.atuvwapps.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Recipe;
import com.atuvwapps.bakingapp.databinding.ActivityStepDetailBinding;
import com.atuvwapps.bakingapp.fragments.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {
    private ActivityStepDetailBinding binding;
    private int stepPosition;
    private Recipe selectedRecipe;
    StepDetailFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStepDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        stepPosition = intent.getIntExtra("Position", 0);
        selectedRecipe = intent.getParcelableExtra("Recipe");
        setTitle(selectedRecipe.getName());
        stepFragment = new StepDetailFragment();
        stepFragment.setChosenStep(stepPosition);
        stepFragment.setChosenRecipe(selectedRecipe);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                stepFragment.releaseVideo();
                finish();
                break;
        }
        return true;
    }
}
