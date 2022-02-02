package com.atuvwapps.bakingapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.atuvwapps.bakingapp.BakingWidgetProvider;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.adapters.IngredientRecyclerViewAdapter;
import com.atuvwapps.bakingapp.adapters.StepRecyclerViewAdapter;
import com.atuvwapps.bakingapp.data.Recipe;

public class RecipeDetailFragment extends Fragment implements StepRecyclerViewAdapter.ItemClickListener {

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private Button widgetButton;
    private Recipe chosenRecipe;
    private StepRecyclerViewAdapter stepAdapter;
    public OnRecipeClickListener mCallback;
    private SharedPreferences sharedPreferences;
    private String WIDGET_KEY = "Widget_key";
    private String WIDGET_TITLE = "Widget_title";
    private String WIDGET_INGREDIENTS = "Widget_ingredients";

    public interface OnRecipeClickListener{
        void onRecipeSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    public RecipeDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle SavedInstanceState) {

        View rootView = inflator.inflate(R.layout.fragment_recipe_detail, container, false);

        ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_rv);
        stepsRecyclerView = rootView.findViewById(R.id.steps_rv);
        ingredientsRecyclerView.setAdapter(new IngredientRecyclerViewAdapter(rootView.getContext(), chosenRecipe.getIngredients()));
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        stepAdapter = new StepRecyclerViewAdapter(rootView.getContext(), chosenRecipe.getSteps(), this);
        stepsRecyclerView.setAdapter(stepAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        sharedPreferences = getContext().getSharedPreferences(WIDGET_KEY, Context.MODE_PRIVATE);
        widgetButton = rootView.findViewById(R.id.add_to_widget_btn);
        widgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to home screen!", Toast.LENGTH_SHORT).show();
                StringBuilder fullIngredients = new StringBuilder();
                for(int i = 0; i < chosenRecipe.getIngredients().size(); i++){
                    fullIngredients.append(chosenRecipe.getIngredients().get(i).getQuantity())
                    .append(chosenRecipe.getIngredients().get(i).getMeasure())
                    .append(" ")
                    .append(chosenRecipe.getIngredients().get(i).getIngredient())
                    .append("\n");
                }
                sharedPreferences.edit().putString(WIDGET_TITLE, chosenRecipe.getName())
                        .putString(WIDGET_INGREDIENTS, fullIngredients.toString())
                        .apply();
                //Update the Widget
                BakingWidgetProvider bakingWidgetProvider = new BakingWidgetProvider();
                bakingWidgetProvider.onUpdate(getContext(), AppWidgetManager.getInstance(getContext()),
                        AppWidgetManager.getInstance(getContext()).getAppWidgetIds(
                                new ComponentName(getContext(), BakingWidgetProvider.class)));
            }
        });

        return rootView;
    }

    public void setChosenRecipe(Recipe recipe){
        chosenRecipe = recipe;
    }

    @Override
    public void onClick(int position) {
        mCallback.onRecipeSelected(position);
    }
}
