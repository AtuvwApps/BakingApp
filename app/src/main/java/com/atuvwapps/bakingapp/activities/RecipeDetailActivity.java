package com.atuvwapps.bakingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Recipe;
import com.atuvwapps.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.atuvwapps.bakingapp.fragments.RecipeDetailFragment;
import com.atuvwapps.bakingapp.fragments.StepDetailFragment;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnRecipeClickListener {

    private Recipe selectedRecipe;
    private ActivityRecipeDetailBinding binding;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        selectedRecipe = intent.getParcelableExtra("Recipe");
        setTitle(selectedRecipe.getName());

        if(binding.recipeDetailContainer != null){
            mTwoPane = true;
            RecipeDetailFragment recipeFragment = new RecipeDetailFragment();
            recipeFragment.setChosenRecipe(selectedRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_container, recipeFragment).commit();
        } else {
            mTwoPane = false;
            RecipeDetailFragment recipeFragment = new RecipeDetailFragment();
            recipeFragment.setChosenRecipe(selectedRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_container, recipeFragment).commit();
        }
    }

    @Override
    public void onRecipeSelected(int position) {
        if(mTwoPane){
            StepDetailFragment stepFragment = new StepDetailFragment();
            stepFragment.setChosenRecipe(selectedRecipe);
            stepFragment.setChosenStep(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_detail_container, stepFragment).commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra("Recipe", selectedRecipe);
            intent.putExtra("Position", position);
            startActivity(intent);
            finish();
        }
    }
}