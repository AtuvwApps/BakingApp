package com.atuvwapps.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Ingredient;
import java.util.List;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Ingredient> ingredients;

    // data is passed into the constructor
    public IngredientRecyclerViewAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient currentIngredient = ingredients.get(position);
        holder.ingredientTextView.setText(currentIngredient.getIngredient());
        holder.quantityTextView.setText(String.valueOf(currentIngredient.getQuantity()));
        holder.measureTextView.setText(currentIngredient.getMeasure());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientTextView;
        public TextView quantityTextView;
        public TextView measureTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.ingredient_tv);
            quantityTextView = itemView.findViewById(R.id.quantity_tv);
            measureTextView = itemView.findViewById(R.id.measure_tv);
        }
    }
}
